package org.example.messages;

public record NewScore(String teamName, String goalScorerName, String assistGiverName, long matchId) {
}
