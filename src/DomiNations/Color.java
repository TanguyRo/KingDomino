package DomiNations;

public class Color {
    private final String name;
    private final String emoji;
    private static final String[] colorsNames;
    private static final String[] colorsEmojis;

    static {
        colorsNames = new String[]{"Rose", "Jaune", "Vert", "Bleu"};
        colorsEmojis = new String[]{"\uD83D\uDFE3", "\uD83D\uDFE1", "\uD83D\uDFE2", "\uD83D\uDD35"};
    }

    public Color(int number) {
        this.name = colorsNames[number - 1];
        this.emoji = colorsEmojis[number - 1];
    }

    public String getName() {
        return name;
    }

    public String getEmoji() {
        return emoji;
    }

}
