package org.example.messages;

import org.example.models.Player;

public record PlayerGoals(String playerName, Player.Gender gender, int goals) {
}
