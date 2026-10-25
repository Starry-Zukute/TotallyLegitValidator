TOTALLY LEGIT VALIDATOR — NETBEANS MAVEN PROJECT

REQUIREMENTS
- JDK 17 or newer
- Apache NetBeans with Maven support

OPEN IN NETBEANS
1. Extract TotallyLegitValidator.zip.
2. In NetBeans, choose File > Open Project.
3. Select the extracted TotallyLegitValidator folder (the folder containing pom.xml).
4. Wait for Maven to load.
5. Right-click the project and choose Run.
6. If prompted for a main class, use:
   com.mygame.validator.Main

PROJECT PACKAGE
com.mygame.validator

CLASSES
- Main.java: starts the application.
- GameFrame.java: manages screen navigation using CardLayout.
- MainMenuPanel.java: Play, Settings, Quit.
- SettingsPanel.java: volume slider placeholder.
- TutorialPanel.java: story and instructions.
- GameplayPanel.java: 3-phase level flow (briefing -> remove one article -> result); see NETBEANS_GUIDE.md
- CollapsibleCard.java: reusable news card that can be minimized/enlarged (main news + 2 articles).
- GameManager.java: credibility, level progression, evaluates the retained article, retry state.
- Article.java: article data and correct classification.
- Level.java: one main briefing plus two articles (exactly one credible); articles are shuffled per level.
- ArticleDatabase.java: ten sample levels, shuffled at the start/retry.

GAME RULES
- Starts at 50% credibility.
- Each level: the MAIN NEWS is shown enlarged for 20 seconds (Continue can skip the wait).
- Then the main news is MINIMIZED in the middle of the screen and the TWO ARTICLES appear
  MINIMIZED at the bottom. Each of the three cards has an Enlarge / Minimize toggle.
- The player REMOVES one article (with a confirmation). The other article is the RETAINED news.
- The game evaluates the retained news: credible = correct (+10 points), fake = incorrect (-10).
- The result and fact-check explanations are shown, then "Next Level" moves on.
- Credibility is clamped between 0% and 100%.
- Ten levels are shuffled each time a new game begins; article A/B order is also random.

CONTENT NOTE
The included article scenarios are illustrative sample game content, not a substitute for
real-world fact-checking. For a school submission, replace or supplement them with sourced,
dated claims and reliable references.

CUSTOMIZATION
Window size is set in GameFrame (1080 x 600).
Change GameManager.STARTING_CREDIBILITY and GameManager.CREDIBILITY_CHANGE to adjust scoring.
Change GameplayPanel.BRIEFING_SECONDS to adjust the briefing timer.
Add or edit sample levels in ArticleDatabase.createLevels().

ASSETS
- src/main/resources/assets/images : paper cards, arrows, bars, timer, dialogue box
- src/main/resources/assets/music  : menu.wav, game.wav (looping)
- src/main/resources/assets/sfx    : 17 sound effects
- Assets.java (loader), PixelUI.java (sprite drawing), AudioManager.java (sound; volume set in Settings)
- See NETBEANS_GUIDE.md section 4b for where each asset is used.

EVENT HANDLERS
- All events use the designer-style handler methods (xxxActionPerformed, ...). See NETBEANS_EVENT_HANDLER_GUIDE.md

OOP STRUCTURE (see NETBEANS_GUIDE.md section 7)
- NewsItem (abstract) -> MainNews; NewsItem -> Article (abstract, implements Scorable) -> CredibleArticle / FakeArticle
- Interfaces: Scorable, Navigator (GameFrame implements), SoundPlayer (AudioManager implements), Screen (the 4 screens)

DESIGN SETTINGS
- Constructors contain no look-and-feel code. Fonts, colors, opaque, borders, icons, sizes and layout
  constraints are NetBeans designer values: see NETBEANS_DESIGN_SETTINGS_GUIDE.md.
- Guides: NETBEANS_GUIDE.md (create the forms), NETBEANS_DESIGN_SETTINGS_GUIDE.md (look),
  NETBEANS_EVENT_HANDLER_GUIDE.md (behavior).
