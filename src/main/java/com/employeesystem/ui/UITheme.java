package com.employeesystem.ui;

import java.awt.*;

/**
 * Central UI theme for a clean, modern look across all frames.
 */
public final class UITheme {

    private UITheme() {}

    // Colors - professional, easy on the eyes
    public static final Color PRIMARY       = new Color(41, 128, 185);   // Blue
    public static final Color PRIMARY_DARK  = new Color(31, 97, 141);
    public static final Color BACKGROUND   = new Color(245, 247, 250);
    public static final Color CARD_BG      = Color.WHITE;
    public static final Color TEXT         = new Color(44, 62, 80);
    public static final Color TEXT_MUTED   = new Color(127, 140, 141);
    public static final Color BORDER       = new Color(189, 195, 199);
    public static final Color SUCCESS      = new Color(39, 174, 96);
    public static final Color ERROR        = new Color(231, 76, 60);

    // Fonts
    public static final Font TITLE_FONT    = new Font("Segoe UI", Font.BOLD, 22);
    public static final Font HEADING_FONT  = new Font("Segoe UI", Font.BOLD, 14);
    public static final Font BODY_FONT     = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font LABEL_FONT    = new Font("Segoe UI", Font.PLAIN, 12);

    // Spacing
    public static final int PADDING_LARGE  = 24;
    public static final int PADDING_MEDIUM = 16;
    public static final int PADDING_SMALL  = 10;
    public static final int GAP            = 12;
    public static final int FIELD_HEIGHT   = 32;
    public static final int BUTTON_HEIGHT  = 36;
}
