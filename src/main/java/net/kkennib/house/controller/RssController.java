package net.kkennib.house.controller;

import net.kkennib.house.model.RssFeed;
import net.kkennib.house.model.RssMedia;
import net.kkennib.house.service.RssService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*")
public class RssController {
    @Autowired
    private RssService rssService;
    @GetMapping("/rss/media/all")
    public Mono<ResponseEntity<List<RssMedia>>> getRssMediaAll() {
        Mono<List<RssMedia>> res = rssService.getRssMediaAll();
        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/rss/media/{name}")
    public Mono<ResponseEntity<List<RssMedia>>> searchRssMedia(
            @PathVariable("name") String mediaName
    ) {
        Mono<List<RssMedia>> res = rssService.searchRssMedia(mediaName);
        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/rss/feed/{mediaIdx}")
    public Mono<ResponseEntity<List<RssFeed>>> getRssFeedListByMedia(
            @PathVariable("mediaIdx") String mediaIdx
    ) {
        Mono<List<RssFeed>> res = rssService.getRssFeedListByMedia(mediaIdx);
        return res.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
