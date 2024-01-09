package org.example.repositories;

import org.example.models.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IScoreRepository extends JpaRepository<Score, Score.PrimaryKey> {
}
