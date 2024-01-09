package org.example.messages;

import org.example.models.Player;

public record PlayerScores(String playerName, Player.Gender gender, int scores) {
}
