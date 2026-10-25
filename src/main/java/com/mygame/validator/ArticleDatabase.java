package com.mygame.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sample game content for the prototype.
 * Replace or expand these examples with researched, cited fact-checking scenarios.
 */
public final class ArticleDatabase {
    private ArticleDatabase() {}

    public static List<Level> createLevels() {
        List<Level> levels = new ArrayList<>();

        levels.add(new Level(
            "Community library extends its opening hours",
            "The city library announced that it will stay open two hours later on weekdays. "
                + "The notice appears on the library's official website.",
            List.of(
                Article.of("Library confirms longer weekday hours",
                    "The library's official announcement lists the new closing time and effective date.",
                    true, "The claim matches the official announcement described in the briefing."),
                Article.of("Library will give every visitor a free laptop tomorrow",
                    "A viral post makes the promise but links to no official notice or credible report.",
                    false, "The post has no reliable source and makes an extraordinary claim.")
            )
        ));

        levels.add(new Level(
            "Local health office shares a seasonal reminder",
            "The local health office published general advice encouraging residents to check "
                + "official public-health guidance during flu season.",
            List.of(
                Article.of("Health office recommends checking official flu guidance",
                    "The message links directly to the local health office's public advisory.",
                    true, "The advice is attributed to the relevant official health office."),
                Article.of("A spoonful of household cleaner prevents the flu",
                    "An anonymous account recommends swallowing a cleaning product as a remedy.",
                    false, "Household cleaners can poison people and are not flu treatments.")
            )
        ));

        levels.add(new Level(
            "Weather agency issues a heavy-rain advisory",
            "The national weather agency has published an advisory for possible heavy rainfall "
                + "in selected areas and advises people to monitor updates.",
            List.of(
                Article.of("Residents should monitor official weather updates",
                    "The advice directs readers to the weather agency's current bulletins.",
                    true, "Checking the official agency is a reliable way to follow changing forecasts."),
                Article.of("A social media post says all storms have been permanently stopped",
                    "The post gives no date, location, source, or evidence.",
                    false, "The absolute claim is unsupported and conflicts with established weather science.")
            )
        ));

        levels.add(new Level(
            "School announces a scheduled campus clean-up",
            "The school posted a clean-up schedule on its official notice board and student portal.",
            List.of(
                Article.of("The campus clean-up is scheduled for Friday",
                    "The date and meeting place match the school's published notice.",
                    true, "The details match the school notice in this scenario."),
                Article.of("Students must pay an urgent fee through an unknown link",
                    "A message threatens students with penalties unless they pay an unfamiliar account.",
                    false, "The message is unverified and uses pressure to encourage payment.")
            )
        ));

        levels.add(new Level(
            "Public transport operator updates its timetable",
            "The transport operator says a route timetable will change next Monday. "
                + "Passengers are advised to review the operator's published schedule.",
            List.of(
                Article.of("The route timetable changes next Monday",
                    "The date and route details match the operator's official timetable.",
                    true, "The claim matches the operator's published schedule in this scenario."),
                Article.of("All public transport will be free forever starting tonight",
                    "An image makes the claim but cites no transport authority or official policy.",
                    false, "A dramatic policy claim needs reliable confirmation.")
            )
        ));

        levels.add(new Level(
            "Museum opens registration for a new exhibit",
            "The museum announced online registration for a limited-time history exhibit.",
            List.of(
                Article.of("Museum registration is available through its official website",
                    "The museum's website lists the exhibit dates and registration instructions.",
                    true, "The information is supported by the museum's official listing."),
                Article.of("Pay a stranger to guarantee a museum admission ticket",
                    "An unverified account asks for money and promises guaranteed entry.",
                    false, "The offer is not confirmed by the museum and may be a scam.")
            )
        ));

        levels.add(new Level(
            "A science center shares an astronomy event",
            "The science center published the date and viewing instructions for a public astronomy event.",
            List.of(
                Article.of("The science center lists a public astronomy viewing event",
                    "The date and venue can be found in the center's own event calendar.",
                    true, "The claim is supported by the event calendar described in this scenario."),
                Article.of("A blurry photo proves a planet will collide with Earth this week",
                    "The post offers no scientific source and urges readers to share it immediately.",
                    false, "A sensational prediction without scientific evidence is not credible.")
            )
        ));

        levels.add(new Level(
            "City office explains how to report a damaged road",
            "The city office published a reporting channel for road hazards and damaged streets.",
            List.of(
                Article.of("Residents can report road hazards through the city office",
                    "The contact details match the city's published service information.",
                    true, "The information matches the official city service notice."),
                Article.of("A post says people should send their passwords to report potholes",
                    "The message asks for account passwords, which are not needed for a road report.",
                    false, "Never share passwords; the request is a major warning sign.")
            )
        ));

        levels.add(new Level(
            "Consumer office warns about suspicious prize messages",
            "The consumer office reminds the public to verify unexpected prize claims before sharing details.",
            List.of(
                Article.of("Unexpected prize messages should be verified",
                    "The advice encourages checking the company through independently found official channels.",
                    true, "Independent verification is a sound way to assess a prize message."),
                Article.of("You won a prize; pay a processing fee to an unknown account now",
                    "The message demands immediate payment but provides no verifiable competition details.",
                    false, "Upfront fees and urgency are common warning signs of a scam.")
            )
        ));

        levels.add(new Level(
            "University publishes a research announcement",
            "The university has posted a research summary and links to the original paper.",
            List.of(
                Article.of("Readers can review the research paper linked by the university",
                    "The announcement provides the researchers' names and a link to the paper.",
                    true, "The original paper and identifiable authors make the claim easier to verify."),
                Article.of("One tiny study proves every food cures every disease",
                    "A repost exaggerates a study and gives no link to the original research.",
                    false, "The claim overgeneralizes and cannot be verified from the provided information.")
            )
        ));

        return levels;
    }

    public static List<Level> shuffledLevels() {
        List<Level> levels = createLevels();
        Collections.shuffle(levels);
        return levels;
    }
}
