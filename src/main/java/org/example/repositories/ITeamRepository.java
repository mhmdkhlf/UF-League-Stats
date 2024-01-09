package org.example.repositories;

import org.example.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ITeamRepository extends JpaRepository<Team, String> {
}
