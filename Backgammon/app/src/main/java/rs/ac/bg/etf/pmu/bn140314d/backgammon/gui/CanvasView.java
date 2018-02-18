package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.R;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities.GameActivity;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.BitmapUtility;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.BoardFeatures;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.FieldGeometryUtility;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.IField;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.ITable;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.logic.PlayerId;

public class CanvasView extends View {
    private GameActivity gameActivity;

    private Bitmap background;
    private Bitmap player1;
    private Bitmap player2;

    private int width;
    private int height;

    private Paint selectedPaint = new Paint();
    {
        selectedPaint.setColor(getResources().getColor(R.color.colorAccent, null));
        selectedPaint.setAlpha(128);
    }

    public CanvasView(Context context) {
        super(context);
    }

    public CanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CanvasView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setGameActivity(GameActivity gameActivity) {
        this.gameActivity = gameActivity;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (getWidth() != width || getHeight() != height) {
            width = getWidth();
            height = getHeight();
            init();
        }

        drawBackground(canvas);
        drawCheckers(canvas);
        drawCurrentChecker(canvas);
        drawSelectedFields(canvas);
    }

    private void init() {
        BoardFeatures boardFeatures = gameActivity.getSettings().getBoardFeatures();
        background = BitmapUtility.scaleBitmap(BitmapFactory.decodeResource(getResources(), gameActivity.getSettings().getWholeBoard()), width, height);
        player1 = BitmapUtility.scaleBitmap(BitmapFactory.decodeResource(getResources(), gameActivity.getSettings().getPlayer1Checker()), (int)(width * boardFeatures.getCheckerWidth()), (int)(height * boardFeatures.getCheckerHeight()));
        player2 = BitmapUtility.scaleBitmap(BitmapFactory.decodeResource(getResources(), gameActivity.getSettings().getPlayer2Checker()), (int)(width * boardFeatures.getCheckerWidth()), (int)(height * boardFeatures.getCheckerHeight()));
    }

    private void drawBackground(Canvas canvas) {
        canvas.drawBitmap(background, 0, 0, null);
    }

    private void drawSelectedFields(Canvas canvas) {
        BoardFeatures boardFeatures = gameActivity.getSettings().getBoardFeatures();
        ArrayList<Integer> selectedFields = gameActivity.getController().getSelectedFields();

        selectedFields.forEach(fieldIndex -> {
            Point point = FieldGeometryUtility.pointFromIndex(fieldIndex);
            Rect rect = FieldGeometryUtility.rectFromPoint(point, width, height, boardFeatures);
            canvas.drawRect(rect, selectedPaint);
        });
    }

    private void drawCheckers(Canvas canvas) {
        GameModel gameModel = gameActivity.getGameModel();
        ITable table = gameModel.getGame().table();

        for (int i = 0; i < ITable.NUMBER_OF_FIELDS; i++) {
            IField field = table.getField(i);
            drawCheckers(canvas, i, field);
        }
    }

    private void drawCheckers(Canvas canvas, int fieldIndex, IField field) {
        int numberOfCheckers = field.getNumberOfChips();
        PlayerId playerId = field.getPlayerId();

        Point point = FieldGeometryUtility.pointFromIndex(fieldIndex);
        Point drawPoint = FieldGeometryUtility.canvasPointFromPoint(point, width, height, gameActivity.getSettings().getBoardFeatures());

        if (playerId == PlayerId.NONE) return;

        Bitmap bitmap = playerId == PlayerId.FIRST ? player1 : player2;

        BoardFeatures boardFeatures = gameActivity.getSettings().getBoardFeatures();
        int heightOfOne = (int)(height * boardFeatures.getCheckerHeight());

        for (int i = 0; i < numberOfCheckers; i++) {
            canvas.drawBitmap(bitmap, drawPoint.x, drawPoint.y, null);
            if (fieldIndex < ITable.NUMBER_OF_FIELDS / 2) {
                drawPoint.y += heightOfOne;
            } else {
                drawPoint.y -= heightOfOne;
            }
        }
    }

    private void drawCurrentChecker(Canvas canvas) {

    }


}
