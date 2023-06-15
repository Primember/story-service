//package vn.sunbuy.storyapi.service;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.rest.webmvc.ResourceNotFoundException;
//import org.springframework.stereotype.Service;
//
//import vn.sunbuy.storyapi.entity.Chapter;
//import vn.sunbuy.storyapi.repository.ChapterRepository;
//@Service
//public class ChapterServiceImpl implements ChapterService {
//	@Autowired
//	private ChapterRepository chapterRepository;
//    @Override
//    public List<Chapter> getAllChapters() {
//        return chapterRepository.findAll();
//    }
// 
//    @Override
//    public Chapter getChapterById(String id) {
//    	return chapterRepository.findById(id).orElseThrow(() -> 
//    	new ResourceNotFoundException(id));  
//    }
//
//    @Override
//    public Chapter updateChapter(Chapter chapter, String id) {
//        Chapter _Chapter = chapterRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Chapter not found"));
//        _Chapter.setChapterTitle(chapter.getChapterTitle());
//        _Chapter.setChapterLink(chapter.getChapterLink());
//        _Chapter.setHtmlContent(chapter.getHtmlContent());
//        return chapterRepository.save(_Chapter);
//    }
//    @Override
//    public void deleteChapter(String id) {
//        chapterRepository.deleteById(id);
//    }
//
//}
