package org.example.repositories;

import org.example.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPlayerRepository extends JpaRepository<Player, String> {
}
