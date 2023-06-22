package vn.sunbuy.storyapi.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
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
import vn.sunbuy.storyapi.repository.StoryRepository;
import vn.sunbuy.storyapi.repository.ViewRepository;

@Service
public class StoryServiceImpl implements StoryService {
	@Autowired
	private StoryRepository storyRepository;
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
      .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy truyện"));
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
	    Pageable pageable = PageRequest.of(page - 1, size, Sort.by("storyName").ascending());
	    TextCriteria textCriteria = TextCriteria.forDefaultLanguage().matchingPhrase(searchText);
	    Page<Story> stories = storyRepository.findBy(textCriteria, pageable);
	    if (stories.isEmpty()) {
	        List<StoriesResult> emptyList = new ArrayList<>();
	        return new PageImpl<>(emptyList, pageable, 0);
	    }

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
	public StoryDetailDTO getStoryDetail(String storyCode, int page, int size) {
	    Query query = new Query(Criteria.where("storyCode").is(storyCode));
	    Story story = mongoTemplate.findOne(query, Story.class);
	    if (story == null) {
	        throw new StoryNotFoundException(storyCode);
	    }

	    List<Story.Chapter> chapters = story.getChapterLinks();
	    int totalChapters = chapters.size();
	    int startIndex = (page - 1) * size;
	    int endIndex = Math.min(startIndex + size, totalChapters);
	    List<StoryDetailDTO.Chapter> pagedChapterDTOs = new ArrayList<>();
	    for (int i = startIndex; i < endIndex; i++) {
	        Story.Chapter chapter = chapters.get(i);
	        StoryDetailDTO.Chapter chapterDTO = new StoryDetailDTO.Chapter();
	        chapterDTO.setTitle(chapter.getTitle());
	        chapterDTO.setLink(chapter.getLink());
	        pagedChapterDTOs.add(chapterDTO);
	    }

	    StoryDetailDTO storyDetailDTO = story.transferStoryDetail(story);
	    storyDetailDTO.setChapterLinks(pagedChapterDTOs);

	    return storyDetailDTO;
	}
	
	@Override
	public List<StoriesDTO> getTopStoriesByTimes(String range) {
	    LocalDate startDate = null;
	    LocalDate endDate = null;
	    switch (range) {
	        case "day":
	            startDate = LocalDate.now();
	            endDate = startDate.plusDays(1);
	            break;
	        case "month":
	            startDate = LocalDate.now().minusDays(30);
	            endDate = LocalDate.now().plusDays(1);
	            break;
	        case "year":
	            startDate = LocalDate.now().minusDays(365);
	            endDate = LocalDate.now().plusDays(1);
	            break;
	        default:
	            throw new StoryNotFoundException(range);
	    }
	    return this.getTopStory(startDate, endDate);
	}

	public List<StoriesDTO> getTopStory(LocalDate startDate, LocalDate endDate) {
	    List<StoriesDTO> storiesDTOs = new ArrayList<StoriesDTO>();
	    Query query = new Query(Criteria.where("date").gte(startDate).lte(endDate));
	    query.limit(5);
	    query.with(Sort.by(Sort.Direction.DESC, "totalViews"));
	    List<View> views = mongoTemplate.find(query, View.class);
	    if (!CollectionUtils.isEmpty(views)) {
	        List<String> storyIds = views.stream().map(view -> view.getStoryId()).collect(Collectors.toList());
	        Iterable<Story> topStories = storyRepository.findAllById(storyIds);
	        topStories.forEach((story) -> {
	            storiesDTOs.add(mapToStoriesDTO(story));
	        });
	    }
	    return storiesDTOs;
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
	public ContentDTO getContentStory(String storyCode, int chapterNumber) {
	    Query query = new Query(Criteria.where("storyCode").is(storyCode));
	    List<ContentDTO> contentDTOs = mongoTemplate.find(query, ContentDTO.class, "content");
	    if (contentDTOs.isEmpty()) {
	        throw new StoryNotFoundException(storyCode);
	    }
	    ContentDTO contentDTO = contentDTOs.get(0);
	    List<ContentDTO.Chapter> chapterDTOs = contentDTO.getContent();
	    if (chapterDTOs.isEmpty() || chapterNumber > chapterDTOs.size()) {
	        throw new RuntimeException("Không tìm thấy chương.");
	    }
	    ContentDTO.Chapter chapterDTO = chapterDTOs.get(chapterNumber - 1);
	    ContentDTO resultDTO = new ContentDTO();
	    resultDTO.setStoryCode(contentDTO.getStoryCode());
	    resultDTO.setStoryName(contentDTO.getStoryName());
	    resultDTO.setAuthor(contentDTO.getAuthor());
	    resultDTO.setCategoryCode(contentDTO.getCategoryCode());
	    resultDTO.setCategoryName(contentDTO.getCategoryName());
	    resultDTO.setStatus(contentDTO.getStatus());
	    resultDTO.setRate(contentDTO.getRate());
	    ContentDTO.Chapter resultChapterDTO = new ContentDTO.Chapter();
	    resultChapterDTO.setChapterTitle(chapterDTO.getChapterTitle());
	    resultChapterDTO.setHtmlContent(chapterDTO.getHtmlContent());
	    resultDTO.setContent(Arrays.asList(resultChapterDTO));
	    return resultDTO;
	}
	@Override
	public List<StoriesDTO> filterByTotalChapters(String range) {
        List<StoriesDTO> storiesDTOList = new ArrayList<>();

        switch (range) {
            case "1":
                storyRepository.findByTotalChaptersLessThan(100).forEach(story -> {
                    storiesDTOList.add(mapToStoriesDTO(story));
                });
                break;
            case "2":
                storyRepository.findByTotalChaptersBetween(100, 500).forEach(story -> {
                    storiesDTOList.add(mapToStoriesDTO(story));
                });
                break;
            case "3":
                storyRepository.findByTotalChaptersBetween(500, 1000).forEach(story -> {
                    storiesDTOList.add(mapToStoriesDTO(story));
                });
                break;
            case "4":
                storyRepository.findByTotalChaptersGreaterThan(1000).forEach(story -> {
                    storiesDTOList.add(mapToStoriesDTO(story));
                });
                break;
            default:
                throw new StoryNotFoundException("Không tìm thấy");
        }

        return storiesDTOList;
    }

    private StoriesDTO mapToStoriesDTO(Story story) {
        StoriesDTO storiesDTO = new StoriesDTO();
        storiesDTO.setId(story.getId());
        storiesDTO.setStoryCode(story.getStoryCode());
        storiesDTO.setStoryName(story.getStoryName());
        storiesDTO.setCategoryDescription(story.getCategoryDescription());
        storiesDTO.setThumbnail(story.getThumbnail());
        storiesDTO.setTotalChapters(story.getTotalChapters());
        return storiesDTO;
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
