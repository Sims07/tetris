package com.ippon.kata.tetris.executing.infrastructure.primary.spring;

import com.ippon.kata.tetris.executing.application.domain.BoardId;
import com.ippon.kata.tetris.executing.application.domain.Boards;
import com.ippon.kata.tetris.shared.domain.GameId;
import com.ippon.kata.tetris.shared.secondary.spring.model.BoardDTO;
import java.util.UUID;
import org.springframework.stereotype.Service;

@Service
public class BoardAPI {
  private final Boards boards;

  public BoardAPI(Boards boards) {
    this.boards = boards;
  }

  public BoardDTO booard(UUID uuid){
      return BoardDTO.from(boards.get(new BoardId(new GameId(uuid))));
  }
}
