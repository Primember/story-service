//package vn.sunbuy.storyapi.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import vn.sunbuy.storyapi.common.URI;
//import vn.sunbuy.storyapi.entity.Chapter;
//import vn.sunbuy.storyapi.repository.ChapterRepository;
//import vn.sunbuy.storyapi.service.ChapterService;
//
//@RestController
//@RequestMapping(URI.API + URI.CHAPTER)
//public class ChapterController {
//	
//	@Autowired
//	ChapterRepository chapterRepository;
//	
////	@Autowired
//	ChapterService chapterService;
//	
//	@PostMapping(URI.CREATE)
//	public Chapter createChapter(@RequestBody Chapter chapter) {
//	  return chapterRepository.save(chapter);
//	}
//	
//	
//	@PutMapping(URI.UPDATE + URI.ID)
//
//    public Chapter updateChapter(@RequestBody Chapter chapter,@PathVariable String id) {
//		  return chapterService.updateChapter(chapter, id);
//
//    }
//	
//	@GetMapping(URI.GETALL)
//	public List<Chapter> getAll(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size) {
//		System.out.println(page + "aaaa" + size);
//		// Pageable pageable = PageRequest.of(page, size);
//		return chapterRepository.findAll();
//	}
//	@DeleteMapping(URI.ID)
//    public void deleteChapter(Chapter Chapter,@PathVariable String id) {
//        chapterRepository.deleteById(id);
//    }
//}
//	
