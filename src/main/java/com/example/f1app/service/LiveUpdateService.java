package com.example.f1app.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
public class LiveUpdateService {
  private final Set<SseEmitter> clients = new CopyOnWriteArraySet<>();

  public SseEmitter subscribe(){
    SseEmitter em = new SseEmitter(0L);
    clients.add(em);
    em.onCompletion(() -> clients.remove(em));
    em.onTimeout(() -> clients.remove(em));
    return em;
  }

  public void broadcast(String text){
    for (SseEmitter em : clients){
      try { em.send(SseEmitter.event().name("live").data(text)); }
      catch (IOException e){ em.complete(); clients.remove(em); }
    }
  }
}
