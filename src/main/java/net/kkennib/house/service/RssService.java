package net.kkennib.house.service;

import net.kkennib.house.model.RssFeed;
import net.kkennib.house.model.RssMedia;
import net.kkennib.house.repository.RssFeedRepository;
import net.kkennib.house.repository.RssMediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RssService {

    @Autowired
    private RssMediaRepository rssMediaRepository;

    @Autowired
    private RssFeedRepository rssFeedRepository;

    public Mono<List<RssMedia>> getRssMediaAll() {
        List<RssMedia> res = rssMediaRepository.findAll();
        return Mono.just(res);
    }

    public Mono<List<RssMedia>> searchRssMedia(String mediaName) {
        List<RssMedia> res = rssMediaRepository.searchByNameLike(mediaName);
        return Mono.just(res);
    }

    public Mono<List<RssFeed>> getRssFeedListByMedia(String mediaIdx) {
        List<RssFeed> res = rssFeedRepository.findByMediaIdx(mediaIdx);
        return Mono.just(res);
    }
}
