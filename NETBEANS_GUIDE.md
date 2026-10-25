# NetBeans Visual Designer Guide: Totally Legit Validator

This guide turns the Swing classes into real NetBeans forms that you edit by drag and drop, and shows
where the project meets the two assignment standards (GUI Builder, OOP pillars). See section 7.

Tested here: the code compiles, the model/OOP behavior passes 18 automated checks, and the full level
flow plays (run headlessly). Not tested here: NetBeans itself, so the designer steps are written from how
NetBeans normally works.

---------------------------------------------------------------------------------------------------
## 1. The idea

| Class | Form type in NetBeans | What you drag in the designer |
|---|---|---|
| `CollapsibleCard` | **JPanel Form** (a reusable bean) | labels, text areas, scroll pane, 2 buttons |
| `MainMenuPanel` | **JPanel Form** | labels + 3 buttons |
| `SettingsPanel` | **JPanel Form** | labels + slider + button |
| `TutorialPanel` | **JPanel Form** | label + text area + 2 buttons |
| `GameplayPanel` | **JPanel Form** | the whole gameplay screen, including 3 `CollapsibleCard`s |
| `GameFrame` | **JFrame Form** | one JPanel named `cards` (layout: CardLayout) |

NOT forms (plain classes, leave alone): `Assets`, `PixelUI`, `AudioManager`, `GameManager`, `Level`,
`NewsItem`, `MainNews`, `Article`, `CredibleArticle`, `FakeArticle`, `ArticleDatabase`, `Main`, and the
interfaces `Navigator`, `Screen`, `SoundPlayer`, `Scorable`.

**Designer = layout and look. Code = behavior.** Everything about how a component looks (fonts, colors,
opaque, alignment, borders, sizes, icons, layout constraints, the card settings) is set in the designer's
**Properties window**; the few things that have no property (the sprite skins) go in the component's
**Code tab -> Post-Initialization Code**. All values are listed in **`NETBEANS_DESIGN_SETTINGS_GUIDE.md`**.
The only code you paste is the event handlers (`NETBEANS_EVENT_HANDLER_GUIDE.md`) and the game flow.
Each `.java` file with a screen contains a block like this:

    // ===== BEGIN STAND-IN FOR THE NETBEANS DESIGNER (delete this whole block in NetBeans) =====
    ...
    // ===== END STAND-IN =====

That block is a hand-written copy of what NetBeans generates (the component variables and
`initComponents()`). It lets the project run as-is. When you rebuild a screen as a real form, you throw
the block away and let NetBeans generate the real one.

**Until you rebuild the screens as forms, the UI is still hand-written, so rule 1 of the assignment is
only met after you follow this guide.**

---------------------------------------------------------------------------------------------------
## 2. Before you start

1. **File -> Open Project** and open the `TotallyLegitValidator` folder (a Maven project, Java 17).
2. Press **Run** once to confirm the game starts. This is your known-good baseline.
3. Make a backup copy of the `src/main/java/com/mygame/validator` folder.
4. Order of work: **CollapsibleCard first**, then the four screens, then `GameFrame` last.

---------------------------------------------------------------------------------------------------
## 3. The routine (repeat for each form)

1. **Move the old file out of the way.** In Projects, right-click e.g. `MainMenuPanel.java` -> Refactor ->
   Rename to `MainMenuPanelOld`, or copy its contents somewhere and delete it. You need the code in step 6.
2. **Create the form.** Right-click the package `com.mygame.validator` -> New -> Other -> Swing GUI Forms
   -> **JPanel Form**. Use the exact class name. Click Finish. It opens in **Design** view.
3. **Drag components** from the **Palette** onto the panel, using the tables in section 4.
4. **Rename every component.** Select it, right-click -> **Change Variable Name**. The names must match the
   table exactly. Also set the visible text in the Properties window (`text` property) if you want.
5. **Arrange the layout** (section 4 gives a suggestion for each screen). Free Design (the default) is fine.
   Then **set every property** (font, alignment, opaque, editable, border, icon, ...) from
   `NETBEANS_DESIGN_SETTINGS_GUIDE.md`. No look-and-feel code is written in the constructors.
6. **Paste the code.** Click the **Source** tab. NetBeans has generated a class header, a constructor
   `public MainMenuPanel() { initComponents(); }`, a grey uneditable `initComponents()` and a "Variables
   declaration" block at the bottom. Do this:
   - Change the header to match the old file, e.g. `public class MainMenuPanel extends javax.swing.JPanel implements Screen`.
   - Replace the generated constructor with the old one. Keep `initComponents();` as its first line.
     Screens take a `Navigator` (not a `GameFrame`): `public MainMenuPanel(Navigator navigator)`.
   - Copy over the other methods (including the `@Override public void onShow()` hooks) and the `import` lines.
   - Do **NOT** copy the STAND-IN block. Do **NOT** touch the grey generated code.
7. **Create the event handlers with the designer's built-in code handler** (double-click a component, or the
   Events tab) and paste the code into the stubs it generates. This is required. The exact code for every
   handler is in **`NETBEANS_EVENT_HANDLER_GUIDE.md`**. The project has no hand-written
   `addActionListener` lines, so there is nothing to delete.
8. Press **Run** and check the screen.

If a name does not match, you get a red underline like "cannot find symbol: variable playButton".
Fix it by renaming the component in the designer (not by editing the code).

---------------------------------------------------------------------------------------------------
## 4. Components per form

Names are case-sensitive. "JLabel" etc. are Palette items under **Swing Controls**.

### 4.1 CollapsibleCard (do this one first)
Create it as a **JPanel Form** and keep its constructor **public and without parameters**:
`public CollapsibleCard() { initComponents(); ... }`. That is what lets the designer create it.

| Variable name | Palette item | Where |
|---|---|---|
| `headerPanel` | JPanel (layout BorderLayout) | North of the card |
| `tagLabel` | JLabel | North of `headerPanel` |
| `headlineArea` | JTextArea | Center of `headerPanel` |
| `toggleButton` | JButton | East of `headerPanel` |
| (no name needed) | JScrollPane | Center of the card |
| `bodyArea` | JTextArea | inside the JScrollPane. Rename the scroll pane `bodyScroll` |
| `removeButton` | JButton | South of the card |

The card's panel itself uses BorderLayout. After pasting the code, do **Clean and Build**
(right-click the project). This compiles the card so that it can be used as a bean.

### 4.2 MainMenuPanel
| Variable name | Palette item | Text |
|---|---|---|
| `titleLabel` | JLabel | TOTALLY LEGIT VALIDATOR |
| `subtitleLabel` | JLabel | Can you tell facts from fake news? |
| `playButton` | JButton | Play |
| `settingsButton` | JButton | Settings |
| `quitButton` | JButton | Quit |
| `footerLabel` | JLabel | Think critically. Check the source. Stop misinformation. |

Layout: stack them top to bottom, centered. Make the three buttons the same width.

### 4.3 SettingsPanel
| Variable name | Palette item | Text / setting |
|---|---|---|
| `titleLabel` | JLabel | Settings |
| `volumeLabel` | JLabel | Volume: 70% |
| `volumeSlider` | JSlider | range, ticks and starting value are Properties-window values (see the design guide); it changes the real game volume |
| `noteLabel` | JLabel | This volume controls the music and the sound effects. |
| `backButton` | JButton | Back to Main Menu |

### 4.4 TutorialPanel
| Variable name | Palette item | Notes |
|---|---|---|
| `titleLabel` | JLabel | Your Mission |
| (no name needed) | JScrollPane | from Swing Containers, fills the middle |
| `storyText` | JTextArea | drop it **inside** the JScrollPane. The story text is its `text` property (full text in the design guide) |
| `backButton` | JButton | Back |
| `startButton` | JButton | Start Game |

### 4.5 GameplayPanel
| Variable name | Palette item | Notes |
|---|---|---|
| `levelLabel` | JLabel | e.g. "Level 1 / 10" (code updates it) |
| `credibilityLabel` | JLabel | e.g. "Credibility: 50%" |
| `credibilityBar` | JProgressBar | sprite skin = Post-Initialization Code (design guide) |
| `timerLabel` | JLabel | the 20-second briefing countdown; clock icon = its `icon` property |
| `timerBar` | JProgressBar | the countdown bar; sprite skin = Post-Initialization Code |
| `centerHolder` | JPanel, layout **GridBagLayout** | holds `mainCard` |
| `mainCard` | **CollapsibleCard** (bean) | dropped into `centerHolder` |
| `articlesPanel` | JPanel, layout **GridBagLayout** | holds the two article cards |
| `articleCardA`, `articleCardB` | **CollapsibleCard** (bean) | dropped into `articlesPanel` |
| `feedbackScroll` | JScrollPane | wraps the feedback box (needs a name: its Post-Init Code refers to it) |
| `feedbackArea` | JTextArea | inside the JScrollPane. About 3 rows high |
| `continueButton` | JButton | Continue (code changes the text per phase) |
| `menuButton` | JButton | Main Menu |

**Adding a CollapsibleCard to the form** (after the Clean and Build from 4.1):
- Expand Source Packages -> `com.mygame.validator` in the Projects window and **drag `CollapsibleCard.java`
  onto the panel** in Design view; or
- right-click `CollapsibleCard.java` -> **Tools -> Add to Palette**, then drag it from the Palette (Beans category).

Set each panel's layout with right-click -> **Set Layout -> GridBagLayout**, and set every card's layout
constraints and bean properties in the designer (values in the design guide). The only constraints the code
changes are the main news card's, at run time, because it has to switch between "minimized in the middle" and
"enlarged to fill"; that happens inside the card's `toggled` event handler and the game flow.

Suggested layout (panel = BorderLayout):
- **North:** a JPanel (GridLayout, 1 row x 3 columns): [JPanel with `credibilityLabel` above `credibilityBar`]
  [`levelLabel`] [JPanel with `timerLabel` above `timerBar`]
- **Center:** `centerHolder` (takes all free space)
- **South:** a JPanel (BorderLayout) with `articlesPanel` at its top, the feedback scroll pane in the
  middle and the two buttons at the bottom

The progress-bar sizes, the clock icon and the sprite skins are designer values too (design guide).

---------------------------------------------------------------------------------------------------
## 4b. The assets (images, music, sound effects)

They live in `src/main/resources/assets/` and are loaded from the classpath, so they work from NetBeans and from
the built jar. If you add files there, use **Clean and Build** so NetBeans copies them.

| Folder | Files | Used for |
|---|---|---|
| `images/` | `paper_card`, `paper_card_alt` | paper background of the main-news card and the two article cards |
| | `arrow_down`, `arrow_up` | icons on the Enlarge (red, down) and Minimize (blue, up) buttons |
| | `bar_frame`, `bar_fill_green`, `bar_fill_red` | credibility bar (green at 50% and above, red below) |
| | `timer_clock`, `timer_bar`, `timer_bar_border` | the briefing countdown (clock icon + bar) |
| | `arrow_down_icon`, `arrow_up_icon`, `timer_clock_icon` | the same icons pre-scaled (20 / 26 px) so the designer's `icon` property can use them directly |
| | `dialogue_box` | pixel frame around the black feedback box |
| `music/` | `menu.wav`, `game.wav` | looping music: menu/settings/tutorial, and during the game |
| `sfx/` | 17 `.wav` files | see the table below |

**How the sprites reach the designer:** icons are normal `icon` properties (Classpath) pointing at the
pre-scaled files `arrow_down_icon.png`, `arrow_up_icon.png` and `timer_clock_icon.png`. The bar skins and the
dialogue-box border are one line each in the component's **Code tab -> Post-Initialization Code**. The paper
background is painted by `CollapsibleCard`'s `paintComponent` override. Nothing is styled in a constructor.

Code files (not forms): `Assets.java` loads/caches the sprites, `PixelUI.java` draws them (nine-slice frames,
bar skin), `AudioManager.java` plays sound (it implements the `SoundPlayer` interface). Anywhere in the code, including a generated handler such as
`playButtonActionPerformed`, you can call `AudioManager.getInstance().play("choice_grab");`.

| Sound effect | When it plays |
|---|---|
| `level_next_select` | Play pressed on the main menu |
| `news_enter` | a level's main news appears |
| `news_exit`, `choice_enter` | main news minimizes, the two articles arrive |
| `description_sound` / `choice_exit` | a card is enlarged / minimized |
| `choice_grab` / `choice_ungrab` | REMOVE pressed / confirmation cancelled |
| `choice_destroy` | removal confirmed |
| `reputation_good` + `emoji_1` | retained article was correct. Also the Settings volume preview |
| `reputation_bad` + `emoji_2` | retained article was wrong |
| `next_level` / `next_level_outro` | next level starts / the last level is finished |
| `next_level_intro`, `reputation_enter` | loaded but not used yet |

Music: `menu` starts when the menu is shown and `game` when a new game starts. The Settings slider
sets one master volume for both music and sound effects. With no audio device the game runs silently.

---------------------------------------------------------------------------------------------------
## 5. GameFrame (the JFrame Form), done last

1. Move the old `GameFrame.java` aside (Refactor -> Rename to `GameFrameOld`).
2. Right-click the package -> New -> Other -> Swing GUI Forms -> **JFrame Form**. Name it `GameFrame`.
3. In Design view, drag one **JPanel** onto the frame so it fills it. Rename it `cards`.
4. Right-click `cards` (in the Navigator window if it is hard to click) -> **Set Layout -> CardLayout**.
5. Source tab. Do these edits:
   - Make the header `public class GameFrame extends javax.swing.JFrame implements Navigator`.
   - Replace the generated constructor with the old one (keep `initComponents();` as its first line).
   - Copy over the fields and the methods `show`, `showMenu`, `showSettings`, `showTutorial`, `startNewGame`
     (the `@Override` ones implement the `Navigator` interface).
   - **Delete the generated `main` method** that NetBeans adds to JFrame Forms. `Main.java` already starts the game.
6. The title, close operation, size (**1080 x 600**), minimum size and centering are designer values (design guide, section on `GameFrame`). The constructor does not set them.
7. Run. The menu should show, and Play -> Start Game -> the first level should work.

How screens are shown: the constructor creates the four screens and adds them to `cards` under the names
`"MENU"`, `"SETTINGS"`, `"TUTORIAL"`, `"GAME"`. When it switches screens it calls `onHide()` on the old one
and `onShow()` on the new one (the `Screen` interface). That is where the menu music starts, the briefing
countdown starts, and the countdown stops when you leave the game.

---------------------------------------------------------------------------------------------------
## 6. Rules that prevent problems

- **Never edit the grey/locked code** NetBeans generates. Change things in the designer instead.
- **Never delete the `initComponents();` call** from a constructor.
- **Do not size the panels.** The frame sets 1080 x 600 and CardLayout stretches the visible screen to fill it.
- **Avoid Absolute Layout.** Stay on Free Design or a standard layout so things resize with the window.
- **Create events only through the designer** (double-click or Events tab) and paste only inside the stub's
  braces. Never type your own `addActionListener` line for a component that has a generated handler: each click would run twice.
- **No look-and-feel code in constructors.** To change how something looks, change its property in the
  designer (or its Post-Initialization Code), never a line in a constructor. Runtime changes (text, colors
  and states that change while the game runs) are inside the event handlers and screen hooks.
- If the designer says it cannot load a form, the usual cause is a `.form` file that no longer matches its
  `.java` after generated code was edited by hand. Restore your backup copy of that one screen and redo it.
- If the designer cannot create a `CollapsibleCard`, you forgot the **Clean and Build**, or the card has lost
  its public no-argument constructor.

---------------------------------------------------------------------------------------------------
## 7. How the project meets the assignment standards

### GUI Design Standard (NetBeans GUI Builder)
- **Form-based design:** every screen and the window are NetBeans forms (JPanel Forms, one JFrame Form),
  including the reusable `CollapsibleCard`, once you follow sections 3-5.
- **GUI code limit (max 25% code-built):** the only UI created in code is the four `new XxxPanel(...)` lines
  in `GameFrame` (they are constructed with arguments), about **7% of the UI instantiation**. Layouts and
  components, including the three news cards and both REMOVE buttons, come from the designer. Look and layout
  values are designer properties; the few custom sprite skins are Post-Initialization Code in the designer's Code tab, not
  hand-written constructor code.
- **Visible components:** every control is in the form's component tree: labels, bars, buttons, text areas,
  scroll panes, the three cards. Dialogs (`JOptionPane`) are the standard Swing ones.

### OOP pillars
| Pillar | Where |
|---|---|
| **Encapsulation** | All fields are `private` and read through getters (`NewsItem.getHeadline()`, `GameManager.getCredibility()`, ...). `GameManager.setCredibility(int)` is private and clamps to 0-100, so no outside code can corrupt the score. `AudioManager.setVolume(float)` validates and clamps. `CollapsibleCard` exposes only small setters (`setTagText`, `setBodyHeight`, `setAccent`, ...) and hides its inner components. Constructors validate (`NewsItem`, `Level`). |
| **Inheritance** | A real hierarchy: `NewsItem` (abstract) -> `MainNews`, and `NewsItem` -> `Article` (abstract) -> `CredibleArticle` / `FakeArticle`. Headline/body storage is written once in `NewsItem` and reused by all. Swing inheritance too: panels `extends JPanel`, `GameFrame extends JFrame`, `CollapsibleCard extends JPanel`, `PixelBarUI extends BasicProgressBarUI`. |
| **Polymorphism** | *Overriding:* `getKindLabel()`, `isCredible()`, `getCredibilityEffect()` and `toString()` are overridden in the subclasses; the screens override `Screen.onShow()/onHide()`; `paintComponent`, `paint`, `paintBorder`. `GameManager.removeArticle` just calls `retained.getCredibilityEffect()`: a `CredibleArticle` returns +10 and a `FakeArticle` returns -10, with no if/else on the type. *Overloading:* `GameManager.removeArticle(int)` / `removeArticle(Article)`; `Level(MainNews, List)` / `Level(String, String, List)`; `CollapsibleCard.setContent(String, String)` / `setContent(NewsItem)`; `SoundPlayer.play(String)` / `play(String, float)`. |
| **Abstraction** | Abstract classes `NewsItem` and `Article` (with abstract `getKindLabel()`, `isCredible()`, `getCredibilityEffect()`). Interfaces: `Scorable` (anything that changes credibility), `Navigator` (screens ask the window to change screen without knowing `GameFrame`), `SoundPlayer` (the game plays sound without knowing `javax.sound`; `AudioManager` is one implementation), `Screen` (show/hide hooks with default methods). |

---------------------------------------------------------------------------------------------------
## 8. Event handlers (required): see NETBEANS_EVENT_HANDLER_GUIDE.md

Every button click, the volume slider and the three news cards' events are wired with handlers generated by
the designer. `NETBEANS_EVENT_HANDLER_GUIDE.md` lists, for every component, which event to create, the stub
name NetBeans generates, and the exact code to paste.

Summary of the handlers (16 in total):

| Form | Handlers |
|---|---|
| `CollapsibleCard` | `toggleButtonActionPerformed`, `removeButtonActionPerformed` |
| `MainMenuPanel` | `playButtonActionPerformed`, `settingsButtonActionPerformed`, `quitButtonActionPerformed` |
| `SettingsPanel` | `volumeSliderStateChanged`, `backButtonActionPerformed` |
| `TutorialPanel` | `backButtonActionPerformed`, `startButtonActionPerformed` |
| `GameplayPanel` | `continueButtonActionPerformed`, `menuButtonActionPerformed`, `articleCardARemoveRequested`, `articleCardBRemoveRequested`, `mainCardToggled`, `articleCardAToggled`, `articleCardBToggled` |

---------------------------------------------------------------------------------------------------
## 9. Final checklist

- [ ] Baseline project ran before you changed anything
- [ ] `CollapsibleCard` rebuilt as a JPanel Form with a public no-argument constructor, then Clean and Build
- [ ] MainMenuPanel, SettingsPanel, TutorialPanel, GameplayPanel recreated as JPanel Forms with the exact names
- [ ] `GameplayPanel`: `centerHolder` and `articlesPanel` are GridBagLayout and contain `mainCard`, `articleCardA`, `articleCardB`
- [ ] Every component renamed exactly as in section 4, and every property set from `NETBEANS_DESIGN_SETTINGS_GUIDE.md`
- [ ] Every event was created with the designer (double-click / Events tab) and its stub filled in from `NETBEANS_EVENT_HANDLER_GUIDE.md`
- [ ] Stand-in blocks are gone from every file (no leftover `// ===== BEGIN STAND-IN` lines)
- [ ] Screens declare `implements Screen`, and take `(Navigator navigator)`; `GameplayPanel` takes `(Navigator, GameManager)`
- [ ] GameFrame is a JFrame Form with a `cards` JPanel set to CardLayout, declares `implements Navigator`, and the generated `main` is deleted
- [ ] `src/main/resources/assets/` is still present (Clean and Build once)
- [ ] Game runs: Menu -> Tutorial -> Level (main news enlarged 20 s) -> main news minimized in the middle, 2 articles minimized at the bottom -> Enlarge/Minimize works on all three -> REMOVE -> result shown -> Next Level
