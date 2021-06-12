package DomiNations;

import javafx.scene.image.ImageView;

public class Cell {
    private final int[] position;
    private boolean isEmpty;
    private LandPiece currentLandPiece;
    private ImageView imageView;


    public Cell(int[] position, boolean isEmpty, LandPiece currentLandPiece) {
        this.position = position;
        this.isEmpty = isEmpty;
        this.currentLandPiece = currentLandPiece;
    }

    public void setEmpty(boolean isEmpty) {
        this.isEmpty = isEmpty;
    }

    public void setCurrentLandPiece(LandPiece currentLandPiece) {
        this.currentLandPiece = currentLandPiece;
    }

    public int[] getPosition() {
        return position;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public LandPiece getCurrentLandPiece() {
        return currentLandPiece;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }
}