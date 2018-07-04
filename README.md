# match-scheduling-round-robin

This project is to build a Round Robin schedule generating program.

The algorithm of organizing which teams to play each other is simple and defined here: https://en.wikipedia.org/wiki/Round-robin_tournament#Scheduling_algorithm

The task is to set identification markers on each Team so we can construct a dynamic schedule generator. A lot of math is involved in this part, such as using combinatorial design to create matchups.

During the task of building the generic algorithm, implementations will have a focus on expansion towards future developments, such as adding a weighted bias towards certain matchups during certain times (e.g. Barcelona vs Real Madrid in mid-season instead of first match of the season).
