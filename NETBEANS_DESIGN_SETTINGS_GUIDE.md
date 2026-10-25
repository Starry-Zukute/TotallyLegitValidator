# NetBeans Design Settings Guide: set the look in the designer, not in code

**Goal:** no constructor contains look-and-feel code (`setFont`, `setOpaque`, `setEditable`, `setLineWrap`,
`setHorizontalAlignment`, `setBorder`, `setIcon`, sizes, layout constraints...). Everything about how a
component looks is set in the NetBeans designer. The project's constructors are now only
`this.navigator = navigator; initComponents();`.

Tested here: a script (`D.java`, 24 checks) builds the screens from the stand-in blocks and confirms every
value in this guide is present. Not tested here: NetBeans itself, so the click paths below describe how
NetBeans normally works. If a label is worded differently in your version, the property name is what matters.

---------------------------------------------------------------------------------------------------
## 1. Where each kind of setting goes

| Kind of setting | Where you set it | Examples |
|---|---|---|
| Plain properties | **Properties window -> Properties tab** | text, font, foreground/background, opaque, editable, lineWrap, horizontalAlignment, border, margin, icon, preferredSize, slider range and ticks |
| Layout details | **Properties window -> Layout tab** of a child, and the layout node of a container in the **Navigator** | GridBag cell, fill, anchor, weights, insets; BorderLayout/GridLayout gaps |
| Settings of a bean | **Properties tab of a `CollapsibleCard`** | tagText, headlineRows, bodyHeight, paper, accent, removeVisible, removeText |
| Things with no property | **Properties window -> Code tab -> Post-Init Code** | the sprite skins of the progress bars, the dialogue-box border |
| Things that change while the game runs | **event handlers** and the screen hooks (already written) | feedback text and colors, which card is open, the countdown, the credibility bar value |

Only three things are still ordinary code, because they are behavior and not a design setting:
the paper background (`CollapsibleCard.paintComponent` override), the Enlarge/Minimize icon swap inside
`CollapsibleCard.setExpanded`, and the four `new XxxPanel(this)` lines in `GameFrame`.

---------------------------------------------------------------------------------------------------
## 2. How to set each kind of value in NetBeans

1. **Select the component** in Design view, or in the **Navigator** window (bottom left, easier for small ones).
2. Open the **Properties** window (Window -> IDE Tools -> Properties, or F4).
3. Find the row, then:

| Value | How |
|---|---|
| **text** | click the field, type, Enter. For a long text (the tutorial story) click the `...` button: it opens a multi-line editor |
| **font** | click `...` -> *Font*: **SansSerif**, then Style (Plain/Bold) and Size |
| **foreground / background** | click `...` -> the color chooser -> **RGB** tab, enter the numbers |
| **opaque, editable, lineWrap, wrapStyleWord, focusable, borderPainted, stringPainted, paintTicks, paintLabels** | tick or untick the checkbox (a ticked box shows as `true`). `focusable` may be under *Other Properties* / expert properties |
| **horizontalAlignment** | dropdown: CENTER, RIGHT, ... |
| **margin** | `...` -> Insets: top, left, bottom, right |
| **preferredSize** | `...` -> width and height |
| **border** | `...` -> choose **Empty Border**, **Line Border** or **Compound Border**, then its numbers/color |
| **icon** | `...` -> *Image Chooser* -> **Classpath** -> pick the file under `/assets/images/` |
| **GridBag cell** | select the child (the card), Properties -> **Layout** tab: gridX, gridY, fill, anchor, weightX, insets |
| **BorderLayout / GridLayout gaps** | in the Navigator select the container's layout node ("BorderLayout") and set hgap / vgap |
| **Post-Init Code** | select the component, Properties -> **Code** tab, row *Post-Init Code* -> `...` -> type the lines. NetBeans copies them into `initComponents()` after that component is fully set up |
| **CollapsibleCard settings** | select the card on the form: its own properties (tagText, headlineRows, ...) are listed in the Properties tab. Clean and Build first so the card is compiled as a bean |

Colors used by the game: ink **40,36,56**, accent blue **40,90,170**, accent gray **130,130,130**, hairline gray **180,180,180**.

---------------------------------------------------------------------------------------------------
## 3. CollapsibleCard (the form that is dropped three times onto GameplayPanel)

Panel itself: **opaque = off**, layout **BorderLayout** with **hgap 6, vgap 6**.

| Component (position) | Property | Value |
|---|---|---|
| `headerPanel` (North) | opaque | off |
| | layout | BorderLayout, hgap 8, vgap 2 |
| `tagLabel` (North of header) | text | CARD |
| | font | SansSerif, Bold, 12 |
| `headlineArea` (Center of header) | editable | off |
| | focusable | off |
| | opaque | off |
| | foreground | 40, 36, 56 |
| | lineWrap / wrapStyleWord | on / on |
| | font | SansSerif, Bold, 15 |
| | columns / rows | 20 / 2 |
| `toggleButton` (East of header) | text | Enlarge |
| | icon | Classpath: `/assets/images/arrow_down_icon.png` |
| | iconTextGap | 8 |
| `bodyScroll` (Center of card) | opaque | off |
| | border | Line Border, color 180,180,180, thickness 1 |
| | **Post-Init Code** | see below |
| `bodyArea` (inside `bodyScroll`) | editable | off |
| | opaque | off |
| | foreground | 40, 36, 56 |
| | lineWrap / wrapStyleWord | on / on |
| | font | SansSerif, Plain, 15 |
| | border | Empty Border, 8, 8, 8, 8 |
| `removeButton` (South of card) | text | REMOVE |

`bodyScroll` -> Code tab -> **Post-Init Code**:

```java
bodyScroll.getViewport().setOpaque(false);
bodyScroll.getViewport().setScrollMode(javax.swing.JViewport.SIMPLE_SCROLL_MODE);
bodyScroll.setVisible(false);
```

(The first two make the paper show through the text without drawing artefacts while scrolling; the third
starts the card minimized.)

---------------------------------------------------------------------------------------------------
## 4. MainMenuPanel

| Component | Property | Value |
|---|---|---|
| `titleLabel` | text | TOTALLY LEGIT VALIDATOR |
| | font | SansSerif, Bold, 30 |
| | horizontalAlignment | CENTER |
| `subtitleLabel` | text | Can you tell facts from fake news? |
| | font | SansSerif, Plain, 16 |
| | horizontalAlignment | CENTER |
| `footerLabel` | text | Think critically. Check the source. Stop misinformation. |
| | horizontalAlignment | CENTER |
| `playButton` / `settingsButton` / `quitButton` | text | Play / Settings / Quit |

Layout: stack the six components top to bottom, centered; select the three buttons -> right-click ->
**Same Size -> Same Width**.

---------------------------------------------------------------------------------------------------
## 5. SettingsPanel

| Component | Property | Value |
|---|---|---|
| `titleLabel` | text | Settings |
| | font | SansSerif, Bold, 28 |
| | horizontalAlignment | CENTER |
| `volumeLabel` | text | Volume: 70% |
| `volumeSlider` | minimum / maximum | 0 / 100 |
| | value | **70** (matches the game's default volume of 0.7) |
| | majorTickSpacing / minorTickSpacing | 25 / 5 |
| | paintTicks / paintLabels | on / on |
| `noteLabel` | text | This volume controls the music and the sound effects. |
| | horizontalAlignment | CENTER |
| `backButton` | text | Back to Main Menu |

The slider's `value` is set in the designer. NetBeans normally applies a component's properties before it
registers that component's handlers, so building the panel should not fire the change handler. If your
version orders it the other way, the only effect is one harmless preview sound when the panel is built
(the volume it sets is the same 70 %).

---------------------------------------------------------------------------------------------------
## 6. TutorialPanel

Panel: layout **BorderLayout** hgap 15, vgap 15; border **Empty Border 25, 35, 25, 35**.

| Component | Property | Value |
|---|---|---|
| `titleLabel` (North) | text | Your Mission |
| | font | SansSerif, Bold, 27 |
| | horizontalAlignment | CENTER |
| scroll pane (Center) | (no settings) | holds `storyText` |
| `storyText` | editable | off |
| | lineWrap / wrapStyleWord | on / on |
| | font | SansSerif, Plain, 16 |
| | margin | 15, 15, 15, 15 |
| | text | the story below |
| `backButton` / `startButton` (South panel) | text | Back / Start Game |

`storyText` -> `text` -> click `...` and paste exactly:

```text
Welcome, Validator!

Misinformation is spreading. Your task is to inspect the news carefully and decide which story deserves to stay.

HOW TO PLAY
1. Read the MAIN NEWS briefing. It is shown enlarged for 20 seconds, but you can continue sooner.
2. The main news then shrinks to the middle of the screen, and TWO ARTICLES appear minimized at the bottom.
3. Click Enlarge on the main news or either article to read it, and Minimize to put it away again.
4. REMOVE the article you think is fake or unsupported. The other article is your retained news.
5. The game then checks whether the retained news is the correct (credible) one: correct = +10 credibility points, incorrect = -10.
6. Credibility is limited to 0%-100%. Complete all 10 levels.

Tip: Check the source, evidence, date, and whether the headline exaggerates the facts. In real life, verify claims with trustworthy independent sources.
```

---------------------------------------------------------------------------------------------------
## 7. GameplayPanel

### 7.1 Containers
| Component | Property | Value |
|---|---|---|
| the panel | layout | BorderLayout, hgap 8, vgap 8 |
| | border | Empty Border 10, 22, 10, 22 |
| North panel | layout | GridLayout, 1 row x 3 columns, hgap 16 |
| `credibilityPanel` and `timerPanel` (inside North) | layout | GridLayout, 2 rows x 1 column, vgap 2 |
| South panel | layout | BorderLayout, hgap 8, vgap 8 |
| `centerHolder` (Center) | layout | **GridBagLayout** |
| `articlesPanel` (North of South) | layout | **GridBagLayout** |
| controls panel (South of South) | layout | FlowLayout (default) |

### 7.2 Labels, bars, buttons
| Component | Property | Value |
|---|---|---|
| `levelLabel` | text | Level 1 / 10 |
| | font | SansSerif, Bold, 20 |
| | horizontalAlignment | CENTER |
| `credibilityLabel` | text | Credibility: 50% |
| | font | SansSerif, Bold, 16 |
| `credibilityBar` | minimum / maximum / value | 0 / 100 / 50 |
| | stringPainted | on |
| | opaque | off |
| | borderPainted | off |
| | preferredSize | 300 x 24 |
| | **Post-Init Code** | see 7.5 |
| `timerLabel` | text | Time: 20s |
| | font | SansSerif, Bold, 16 |
| | horizontalAlignment | RIGHT |
| | icon | Classpath: `/assets/images/timer_clock_icon.png` |
| `timerBar` | minimum / maximum | 0 / **200** (20 seconds in tenths) |
| | stringPainted | off |
| | opaque | off |
| | borderPainted | off |
| | preferredSize | 300 x 20 |
| | **Post-Init Code** | see 7.5 |
| `continueButton` | text | Continue |
| `menuButton` | text | Main Menu |

If you change `BRIEFING_SECONDS` in the code, change `timerBar`'s maximum to `BRIEFING_SECONDS * 10`.

### 7.3 The three cards (CollapsibleCard beans)
Select each card on the form and set its bean properties:

| Card | tagText | headlineRows | bodyHeight | paper | accent | removeVisible | removeText |
|---|---|---|---|---|---|---|---|
| `mainCard` | MAIN NEWS | **3** | 0 | paper_card | **40, 90, 170** | **off** | (unused) |
| `articleCardA` | ARTICLE A | 2 | **85** | **paper_card_alt** | **130, 130, 130** | on | REMOVE ARTICLE A |
| `articleCardB` | ARTICLE B | 2 | **85** | **paper_card_alt** | **130, 130, 130** | on | REMOVE ARTICLE B |

(Values in bold differ from the card's defaults. Properties left at their default need no action.)

### 7.4 GridBag cells (Properties -> Layout tab of each card)
| Card (parent) | gridX | gridY | fill | anchor | weightX | weightY | insets (top, left, bottom, right) |
|---|---|---|---|---|---|---|---|
| `mainCard` (`centerHolder`) | 0 | 0 | Horizontal | Center | 1.0 | 0 | 0, 0, 0, 0 |
| `articleCardA` (`articlesPanel`) | 0 | 0 | Horizontal | **North** | 0.5 | 0 | 0, 0, 0, 6 |
| `articleCardB` (`articlesPanel`) | 1 | 0 | Horizontal | **North** | 0.5 | 0 | 0, 6, 0, 0 |

The main news card's cell is the "minimized in the middle" layout. The game changes it at run time (to fill
the area while enlarged) inside the card's `toggled` handler and the game-flow methods. That is the one layout
value that is not static.

### 7.5 Feedback box and Post-Init Code
| Component | Property | Value |
|---|---|---|
| `feedbackScroll` (name the JScrollPane) | **Post-Init Code** | see below |
| `feedbackArea` | background | 0, 0, 0 |
| | foreground | 255, 255, 255 |
| | editable | off |
| | focusable | off |
| | lineWrap / wrapStyleWord | on / on |
| | font | SansSerif, Plain, 14 |
| | margin | 2, 6, 2, 6 |
| | columns / rows | 40 / 3 |

**`credibilityBar` -> Code tab -> Post-Init Code:**

```java
credibilityBar.setUI(new PixelUI.PixelBarUI(
        () -> Assets.trimmed(credibilityBar.getValue() >= 50 ? "bar_fill_green" : "bar_fill_red"),
        Assets.trimmed("bar_frame")));
```

**`timerBar` -> Code tab -> Post-Init Code:**

```java
timerBar.setUI(new PixelUI.PixelBarUI(
        () -> Assets.trimmed("timer_bar"), Assets.trimmed("timer_bar_border")));
```

**`feedbackScroll` -> Code tab -> Post-Init Code:**

```java
feedbackScroll.setBorder(new PixelUI.NineSliceBorder(Assets.trimmed("dialogue_box"), 6, 8));
feedbackScroll.getViewport().setBackground(java.awt.Color.BLACK);
```

`PixelUI` and `Assets` are in the same package as the form, so no import is needed. A progress bar has no
"skin" property, which is why these three snippets are the designer's Post-Init Code instead of properties.

---------------------------------------------------------------------------------------------------
## 8. GameFrame (JFrame Form)

| Where | Property | Value |
|---|---|---|
| `GameFrame` (the frame) | title | Totally Legit Validator |
| | defaultCloseOperation | EXIT_ON_CLOSE |
| | minimumSize | 1080 x 600 |
| form **Code tab** | Generate Center | on |
| | Form Size Policy / size | Generate Resize Code, size **1080 x 600** |
| `cards` (JPanel filling the frame) | layout | **CardLayout** |

If your NetBeans words the size options differently, the same result is one line in the constructor for
the frame only: `setSize(1080, 600);`. Leave `setLocationRelativeTo(null)` to the *Generate Center* checkbox.

---------------------------------------------------------------------------------------------------
## 9. What is intentionally still code (and why)

| Code | Why it is not a designer setting |
|---|---|
| `CollapsibleCard.paintComponent` | draws the paper sprite: custom painting, an override (polymorphism) |
| `CollapsibleCard.setExpanded` icon swap | the icon changes while the game runs |
| `GameplayPanel` flow methods (`showBriefing`, `onRemoveClicked`, `updateStatus`, ...) | they change text, colors and which card is open as the level progresses; they are called from the event handlers and screen hooks |
| `layoutMainCard()` | changes one GridBag cell at run time |
| `new XxxPanel(this)` in `GameFrame` | the screens take constructor arguments (navigator, game manager) so the designer cannot create them |
| `articleCards = new CollapsibleCard[] {...}` | a convenience array, not UI |

---------------------------------------------------------------------------------------------------
## 10. Checklist

- [ ] No constructor contains `setFont`, `setOpaque`, `setEditable`, `setLineWrap`, `setBorder`, `setIcon`, `setPreferredSize` or similar
- [ ] Every property in sections 3-8 is set in the Properties window
- [ ] The three Post-Init Code snippets are in section 7.5, and the three lines in section 3
- [ ] The three icon files are chosen with the *Classpath* option, not typed as code
- [ ] `feedbackScroll` exists as a name (Post-Init Code refers to it)
- [ ] The three cards have their bean properties and GridBag cells set
- [ ] The game looks the same as before: pixel bars, paper cards, black dialogue box, Enlarge/Minimize arrows
