package org.example.repositories;

import java.util.List;

import org.example.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IMatchRepository extends JpaRepository<Match, Long> {

   List<Match> findAllByHomeTeamNameOrAwayTeamName(String homeTeamName, String awayTeamName);

   List<Match> findAllByHomeTeamName(String homeTeamName);

   List<Match> findAllByAwayTeamName(String awayTeamName);
}
