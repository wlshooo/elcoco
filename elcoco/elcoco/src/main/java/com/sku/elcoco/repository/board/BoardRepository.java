package com.sku.elcoco.repository.board;

import com.sku.elcoco.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {

}
