package vn.sunbuy.storyapi.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import vn.sunbuy.storyapi.entity.Story;
import vn.sunbuy.storyapi.entity.View;
import vn.sunbuy.storyapi.exception.StoryNotFoundException;
import vn.sunbuy.storyapi.model.ContentDTO;
import vn.sunbuy.storyapi.model.FullStoriesDTO;
import vn.sunbuy.storyapi.model.StoriesDTO;
import vn.sunbuy.storyapi.model.StoriesResult;
import vn.sunbuy.storyapi.model.StoryDetailDTO;
import vn.sunbuy.storyapi.model.ViewCount;
import vn.sunbuy.storyapi.repository.AuthorRepository;
import vn.sunbuy.storyapi.repository.CategoryRepository;
import vn.sunbuy.storyapi.repository.StoryRepository;
import vn.sunbuy.storyapi.repository.ViewRepository;

@Service
public class StoryServiceImpl implements StoryService {
	@Autowired
	private StoryRepository storyRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private ViewRepository viewRepository;
	@Autowired
	MongoTemplate mongoTemplate;
	@Autowired
	AuthorRepository authorRepository;
	@Override
	public Page<StoriesDTO> getAllStories(int page, int size) {
		Pageable pageable = PageRequest.of(page-1, size);
        Page<Story> stories = storyRepository.findAll(pageable);
		List<StoriesDTO> storiesDTOs = stories.stream()
		    .map(story -> story.transferStories(story))
		    .collect(Collectors.toList());
		return new PageImpl<>(storiesDTOs, pageable, stories.getTotalElements());
	}
	@Override
	public Story createStory(Story story) {
		story.setCreatedAt(LocalDateTime.now());
		story.setUpdatedAt(LocalDateTime.now());
        return storyRepository.save(story);
	}

	@Override
	public Story getStoryById(String id) {
		return storyRepository.findById(id).orElseThrow(() -> 
		new ResourceNotFoundException(id)); 
	}

	@Override
	public Story updateStory(Story story, String id) {
      Story _story = storyRepository.findById(id)
      .orElseThrow(() -> new ResourceNotFoundException("Story not found"));
		_story.setStoryLink(_story.getStoryLink());
		_story.setStoryCode(story.getStoryCode());
		_story.setStoryName(story.getStoryName());
		_story.setThumbnail(story.getThumbnail());
		_story.setAuthor(story.getAuthor());
		_story.setTotalChapters(story.getTotalChapters());
		_story.setCategoryCode(story.getCategoryCode());
		_story.setInfo(story.getInfo());
		_story.setStatus(story.getStatus());
		_story.setOrigin(story.getOrigin());
		return storyRepository.save(_story);
	}

	@Override
	public Page<StoriesDTO> getNewStories(Pageable page) {
		Query queryGetNew = new Query();
		Pageable sortedBy = PageRequest.of(page.getPageNumber()-1, page.getPageSize(), Sort.by("createdAt").descending());
		queryGetNew.with(sortedBy);
		List<Story> newStories = mongoTemplate.find(queryGetNew, Story.class);
		Page<Story> patientPage = PageableExecutionUtils.getPage(
				newStories,
				sortedBy,
		        () -> mongoTemplate.count(queryGetNew, Story.class));
		Page<StoriesDTO> storiesDTO = patientPage.map(StoriesDTO::transfer);
		return storiesDTO;
	}

	@Override
	public Page<StoriesDTO> getCompletedStories(Pageable page) {
		Query queryGetFinish = new Query(Criteria.where("status").is("Full"));	
		Pageable pageable = PageRequest.of(page.getPageNumber()-1, page.getPageSize());
		Sort sort = Sort.by("createdAt").descending();
		queryGetFinish.with(sort);
		queryGetFinish.with(pageable);
		List<Story> completedStories = mongoTemplate.find(queryGetFinish, Story.class);
		Page<Story> patientPage = PageableExecutionUtils.getPage(
				completedStories,
				pageable,
		        () -> mongoTemplate.count(queryGetFinish, Story.class));
		Page<StoriesDTO> storiesDTO = patientPage.map(StoriesDTO::transfer);
		return storiesDTO;
	}
	@Override
	public Page<StoriesResult> searchStories(String searchText, int page, int size) {
		Pageable pageable = PageRequest.of(page-1, size, Sort.by("storyName").ascending());
        Page<Story> stories = storyRepository.findAllByStoryNameContainingIgnoreCase(searchText, pageable);
        return stories.map(StoriesResult::transfer);
    }
	@Override
	public Page<Story> getStoryByCategory(String categoryCode, Pageable pageable) {
		return storyRepository.findByCategoryCode(categoryCode, pageable);
	}

	@Override
	public Page<StoriesDTO> getTopStoryByCategory(String categoryId, Pageable page) {

		return null;
	}

	@Override
	public StoryDetailDTO getStoryDetail(String storyId) {
		Query query = new Query(Criteria.where("_id").is(storyId));
        Story story = mongoTemplate.findOne(query, Story.class);
        if (story == null) {
            throw new StoryNotFoundException("Story not found!");
        }
        return story.transferStoryDetail(story);
	}
	@Override
	public List<StoriesDTO> getTopStoriesByDay() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<StoriesDTO> getTopStoriesByMonth() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StoriesDTO> getTopStoriesByYear() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateViewStory(String storyId) {
		
		Story story = storyRepository.findById(storyId).orElseThrow(() -> new StoryNotFoundException(storyId));
		if (story.getTotalViews() == null) {
			story.setTotalViews(Long.valueOf(1));
		} else {
			story.setTotalViews(story.getTotalViews() + 1);
		}
		story.setUpdatedAt(LocalDateTime.now());
		storyRepository.save(story);
		
		Query query = new Query(Criteria.where("storyId").is(storyId).and("date").gte(LocalDate.now()));
		List<View> views = mongoTemplate.find(query, View.class);
		if (!CollectionUtils.isEmpty(views)) {
			View view = views.get(0);
			view.setTotalView(view.getTotalView() + 1);
			view.setUpdatedAt(LocalDateTime.now());
			viewRepository.save(view);
		} else {
			View view = new View();
			view.setStoryId(storyId);
			view.setTotalView(Long.valueOf(1));
			view.setDate(LocalDate.now());
			view.setCreatedAt(LocalDateTime.now());
			view.setUpdatedAt(LocalDateTime.now());
			viewRepository.save(view);
		}
	}

	@Override
	public Page<ContentDTO.Chapter> getContentStory(String storyCode, Pageable pageable) {
		Query query = new Query(Criteria.where("storyCode").is(storyCode));
        query.fields().slice("content", (int)pageable.getOffset(), pageable.getPageSize());
        List<Document> contentDocuments = mongoTemplate.find(query, Document.class, "content");
        if (contentDocuments.isEmpty()) {
            throw new RuntimeException("Story not found");
        }
        List<Document> chapterDocuments = contentDocuments.get(0).getList("content", Document.class);
        List<ContentDTO.Chapter> chapters = new ArrayList<>();
        for (Document chapterDocument : chapterDocuments) {
            ContentDTO.Chapter chapter = new ContentDTO.Chapter();
            chapter.setChapterTitle(chapterDocument.getString("chapterTitle"));
            chapter.setHtmlContent(chapterDocument.getString("htmlContent"));
            chapters.add(chapter);
        }
        return new PageImpl<>(chapters, pageable, chapterDocuments.size());
	}
	@Override
	public void deleteStory(String id) {
		storyRepository.deleteById(id);
	}

	@Override
	public FullStoriesDTO getTopAndFullStory() {
		return null;
	}




	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	   
//	@Override
//	public FullStoriesDTO getTopAndFullStory() {
//		FullStoriesDTO fullStoriesDTO = new FullStoriesDTO();
//		Query queryGetTop = new Query();
//		queryGetTop.limit(10);
//		queryGetTop.with(Sort.by(Sort.Direction.DESC, "totalViews"));
//		List<Story> topStories = mongoTemplate.find(queryGetTop, Story.class);
//		if (!CollectionUtils.isEmpty(topStories)) {
//			fullStoriesDTO.setTopStories(topStories.stream().map(story -> transfer(story)).collect(Collectors.toList()));
//		}
//		Query queryGetNew = new Query();
//		queryGetNew.limit(16);
//		queryGetNew.with(Sort.by(Sort.Direction.DESC, "createdAt"));
//		List<Story> newStories = mongoTemplate.find(queryGetNew, Story.class);
//		if (!CollectionUtils.isEmpty(newStories)) {
//			fullStoriesDTO.setNewStories(newStories.stream().map(story -> transfer(story)).collect(Collectors.toList()));
//		}
//		Query queryGetFinish = new Query(Criteria.where("status").is("FULL"));
//		queryGetFinish.limit(10);
//		queryGetFinish.with(Sort.by(Sort.Direction.DESC, "createdAt"));
//		List<Story> completedStories = mongoTemplate.find(queryGetFinish, Story.class);
//		if (!CollectionUtils.isEmpty(completedStories)) {
//			fullStoriesDTO.setCompletedStories(completedStories.stream().map(story -> transfer(story)).collect(Collectors.toList()));
//		}
//		
//		// get top story by day
////		Query queryGetTopByDay = new Query(Criteria.where("views").elemMatch(Criteria.where("date").gte(LocalDate.now())));
////		List<Story> topStoryByDays = mongoTemplate.find(queryGetTopByDay, Story.class);
////		if (!CollectionUtils.isEmpty(topStoryByDays)) {
////			topStoryByDays.sort(new Comparator<Story>() {
////				@Override
////				public int compare(Story o1, Story o2) {
////					Long viewO1 = o1.getViews().stream().filter(v -> v.getDate().equals(LocalDate.now())).findFirst().get().getTotal();
////					Long viewO2 = o2.getViews().stream().filter(v -> v.getDate().equals(LocalDate.now())).findFirst().get().getTotal();
////					if (viewO1.equals(viewO2)) {
////						return 0;
////					} else if (viewO1 > viewO2) {
////						return 1;
////					} else {
////						return -1;
////					}
////				}
////			});
////			fullStoriesDTO.setTopStoryByDays(topStoryByDays.stream().map(story -> transfer(story)).limit(10).collect(Collectors.toList()));
////		}
//		Query queryGetTopViewByDay = new Query(Criteria.where("date").gte(LocalDate.now()));
//		queryGetTopViewByDay.limit(5);
//		queryGetTopViewByDay.with(Sort.by(Sort.Direction.DESC, "totalView"));
////		queryGetTopViewByDay.with(Sort.by(Sort.Direction.DESC, "date"));
//		List<View> views = mongoTemplate.find(queryGetTopViewByDay, View.class);
//		if (!CollectionUtils.isEmpty(views)) {
//			List<String> storyIds = views.stream().map(view -> view.getStoryId()).collect(Collectors.toList());
//			Iterable<Story> topStoryByDay = storyRepository.findAllById(storyIds);
//			List<StoriesDTO> storyDTOs = new ArrayList<StoriesDTO>();
//			topStoryByDay.forEach((story) -> {
//				StoriesDTO storyDTO = transfer(story);
//				List<String> categoryIds = story.getCategoryIds();
//				Iterable<Category> categorys = categoryRepository.findAllById(categoryIds);
//				List<CategoryDTO> categoryDtos = new ArrayList<CategoryDTO>();
//				categorys.forEach((category) -> {
//					categoryDtos.add(category.transfer(category));
//				});
//				storyDTO.setCategorys(categoryDtos);
//				storyDTOs.add(storyDTO);
//			});
//			fullStoriesDTO.setTopStoryByDays(storyDTOs);
//		}
//		List<Category> categorys = categoryRepository.findAll(Sort.by(Sort.Direction.DESC, "categoryName"));
//		if (!CollectionUtils.isEmpty(categorys)) {
//			fullStoriesDTO.setCategorys(categorys.stream().map(category -> category.transfer(category)).collect(Collectors.toList()));
//		}
//		return fullStoriesDTO;
//	}	
//	@Override
//	public List<StoriesDTO> getTopStoriesByDay() {
//		
//		List<StoriesDTO> storiesDTOs = new ArrayList<StoriesDTO>();
////		Query queryGetTopByDay = new Query(Criteria.where("views").elemMatch(Criteria.where("date").gte(LocalDate.now())));
////		List<Story> topStoryByDays = mongoTemplate.find(queryGetTopByDay, Story.class);
////		if (!CollectionUtils.isEmpty(topStoryByDays)) {
////			topStoryByDays.sort(new Comparator<Story>() {
////				@Override
////				public int compare(Story o1, Story o2) {
////					Long viewO1 = o1.getViews().stream().filter(v -> v.getDate().equals(LocalDate.now())).findFirst().get().getTotal();
////					Long viewO2 = o2.getViews().stream().filter(v -> v.getDate().equals(LocalDate.now())).findFirst().get().getTotal();
////					if (viewO1.equals(viewO2)) {
////						return 0;
////					} else if (viewO1 > viewO2) {
////						return 1;
////					} else {
////						return -1;
////					}
////				}
////			});
////			storiesDTOs = topStoryByDays.stream().map(story -> transfer(story)).limit(5).collect(Collectors.toList());
////		}
//		Query queryGetTopViewByDay = new Query(Criteria.where("date").gte(LocalDate.now()));
//		queryGetTopViewByDay.limit(5);
//		queryGetTopViewByDay.with(Sort.by(Sort.Direction.DESC, "totalView"));
////		queryGetTopViewByDay.with(Sort.by(Sort.Direction.DESC, "date"));
//		List<View> views = mongoTemplate.find(queryGetTopViewByDay, View.class);
//		if (!CollectionUtils.isEmpty(views)) {
//			List<String> storyIds = views.stream().map(view -> view.getStoryId()).collect(Collectors.toList());
//			Iterable<Story> topStoryByDay = storyRepository.findAllById(storyIds);
//			topStoryByDay.forEach((story) -> {
//				storiesDTOs.add(transfer(story));
//			});
//		}
//		return storiesDTOs;
//	}
//	@Override
//	public List<StoriesDTO> getTopStoriesByMonth() {
//		LocalDate startDate = LocalDate.now().minusDays(30);
//		LocalDate endDate = LocalDate.now().plusDays(1);
//		return this.getTopStory(startDate, endDate);
//	}
//
//	@Override
//	public List<StoriesDTO> getTopStoriesByYear() {
//		LocalDate startDate = LocalDate.now().minusDays(365);
//		LocalDate endDate = LocalDate.now().plusDays(1);
//		return this.getTopStory(startDate, endDate);
//	}	
//	public  List<StoriesDTO> getTopStory(LocalDate startDate, LocalDate endDate) {
//		
//		List<StoriesDTO> storiesDTOs = new ArrayList<StoriesDTO>();
//		Aggregation aggregation = Aggregation.newAggregation(
//				Aggregation.match(Criteria.where("date").gte(startDate).lt(endDate)),
//				Aggregation.group("storyId").sum("totalView").as("total").addToSet("storyId").as("storyId")
//			);
//			AggregationResults<ViewCount> groupResults = mongoTemplate.aggregate(aggregation, View.class, ViewCount.class);
//			List<ViewCount> mapResult = groupResults.getMappedResults();
//			List<ViewCount> viewCounts = new ArrayList<ViewCount>(mapResult);
//			if (!CollectionUtils.isEmpty(viewCounts)) {
//				// System.out.print(viewCounts.stream().map(view -> view.getStoryId()).limit(5).collect(Collectors.toList()).toString());
//				viewCounts.sort(new Comparator<ViewCount>() {
//					@Override
//					public int compare(ViewCount o1, ViewCount o2) {
//						return o2.getTotal().compareTo(o1.getTotal());
//					}				
//				});
//				System.out.print(viewCounts.toString());
//				List<String> storyIds = viewCounts.stream().map(view -> view.getStoryId()).limit(5).collect(Collectors.toList());
//				// Collections.reverse(storyIds);
//				// System.out.print(storyIds.toString());
//				Iterable<Story> topStoryByDay = storyRepository.findAllById(storyIds);
//				List<Story> storys = StreamSupport.stream(topStoryByDay.spliterator(), false).collect(Collectors.toList());
//				storyIds.forEach((storyId) -> {
//					Story story = storys.stream().filter(s -> s.getId().equals(storyId)).findFirst().get();
//					StoriesDTO storyDTO = transfer(story);
//					List<String> categoryIds = story.getCategoryIds();
//					Iterable<Category> categorys = categoryRepository.findAllById(categoryIds);
//					List<CategoryDTO> categoryDtos = new ArrayList<CategoryDTO>();
//					categorys.forEach((category) -> {
//						categoryDtos.add(category.transfer(category));
//					});
//					storyDTO.setCategorys(categoryDtos);
//					storiesDTOs.add(storyDTO);
//				});
//			}
//			return storiesDTOs;
//	}
//	@Override

//	@Override
//	public Page<StoriesDTO> getTopStoryByCategory(String categoryId, Pageable page) {
//		List<String> categoryIds = new ArrayList<String>();
//		categoryIds.add(categoryId);
//		Query query = new Query(Criteria.where("categoryIds").all(categoryIds));
//		query.with(Sort.by(Sort.Direction.DESC, "totalView"));
//		Pageable sortedBy = PageRequest.of(page.getPageNumber(), page.getPageSize(), Sort.by("createdAt").descending().and(Sort.by("storyName")));
//		query.with(sortedBy);
//		List<Story> stories = mongoTemplate.find(query, Story.class);
//		Page<Story> patientPage = PageableExecutionUtils.getPage(
//				stories,
//				sortedBy,
//		        () -> mongoTemplate.count(query, Story.class));
//		Page<StoriesDTO> storiesDTO = patientPage.map(this::transfer);
//		return storiesDTO;
//	}
}
