CREATE TABLE TEAM
(
    name VARCHAR(64) NOT NULL,
    PRIMARY KEY (name)
);

CREATE TABLE PLAYER
(
    fullName VARCHAR(64)             NOT NULL,
    gender   ENUM ('Female', 'Male') NOT NULL,
    teamName VARCHAR(64)             NOT NULL,
    PRIMARY KEY (fullName),
    FOREIGN KEY (teamName) REFERENCES TEAM (name)
);

CREATE TABLE MATCH
(
    id           IDENTITY,
    homeTeamName VARCHAR(64) NOT NULL,
    awayTeamName VARCHAR(64) NOT NULL,
    matchStatus  Enum ('Upcoming', 'Ongoing', 'Done'),
    PRIMARY KEY (id),
    FOREIGN KEY (homeTeamName) REFERENCES TEAM (name),
    FOREIGN KEY (awayTeamName) REFERENCES TEAM (name)
);

CREATE TABLE SCORE
(
    matchId         BIGINT      NOT NULL,
    index           INT         NOT NULL,
    teamName        VARCHAR(64) NOT NULL,
    goalScorerName  VARCHAR(64),
    assistGiverName VARCHAR(64),
    PRIMARY KEY (matchId, index),
    FOREIGN KEY (teamName) REFERENCES TEAM (name),
    FOREIGN KEY (goalScorerName) REFERENCES PLAYER (fullName),
    FOREIGN KEY (assistGiverName) REFERENCES PLAYER (fullName),
    FOREIGN KEY (matchId) REFERENCES MATCH (id)
);
