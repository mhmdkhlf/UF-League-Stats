package org.example.messages;

import org.example.models.Player;

public record PlayerAssists(String playerName, Player.Gender gender, int assists) {
}
