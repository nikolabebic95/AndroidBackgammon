package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.R;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.activities.GameActivity;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.BitmapUtility;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.BoardFeatures;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.GeometryUtility;
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
    private Paint textPaint = new Paint();
    {
        selectedPaint.setColor(getResources().getColor(R.color.colorAccent, null));
        selectedPaint.setAlpha(128);

        textPaint.setColor(getResources().getColor(R.color.colorButtonPressed, null));
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(Typeface.create("casual", Typeface.BOLD));

        setOnTouchListener(((view, motionEvent) -> {
            view.performClick();

            float x = motionEvent.getX();
            float y = motionEvent.getY();

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    gameActivity.getController().onPointerDown(x, y);
                    break;
                case MotionEvent.ACTION_MOVE:
                    gameActivity.getController().onPointerMove(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    gameActivity.getController().onPointerUp(x, y);
                    break;
            }

            return true;
        }));
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
        drawBar(canvas);
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
            Rect rect;
            if (fieldIndex == -1) {
                rect = GeometryUtility.barRectFromPlayerId(gameActivity.getGameModel().getCurrentPlayer(), width, height, boardFeatures);
            } else {
                Point point = GeometryUtility.pointFromIndex(fieldIndex);
                rect = GeometryUtility.rectFromPoint(point, width, height, boardFeatures);
            }

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

        Point point = GeometryUtility.pointFromIndex(fieldIndex);
        Point drawPoint = GeometryUtility.canvasPointFromPoint(point, width, height, gameActivity.getSettings().getBoardFeatures());

        if (playerId == PlayerId.NONE) return;

        Bitmap bitmap = playerId == PlayerId.FIRST ? player1 : player2;

        BoardFeatures boardFeatures = gameActivity.getSettings().getBoardFeatures();
        int widthOfOne = (int)(width * boardFeatures.getCheckerWidth());
        int heightOfOne = (int)(height * boardFeatures.getCheckerHeight());

        for (int i = 0; i < boardFeatures.getMaxCheckersOnField() && i < numberOfCheckers; i++) {
            canvas.drawBitmap(bitmap, drawPoint.x, drawPoint.y, null);
            if (fieldIndex < ITable.NUMBER_OF_FIELDS / 2) {
                drawPoint.y += heightOfOne;
            } else {
                drawPoint.y -= heightOfOne;
            }
        }

        if (numberOfCheckers > boardFeatures.getMaxCheckersOnField()) {
            if (fieldIndex < ITable.NUMBER_OF_FIELDS / 2) {
                drawPoint.y -= heightOfOne;
            } else {
                drawPoint.y += heightOfOne;
            }

            drawPoint.x += widthOfOne / 2;
            drawPoint.y += heightOfOne;
            textPaint.setTextSize(heightOfOne);
            canvas.drawText(numberOfCheckers + "", drawPoint.x, drawPoint.y, textPaint);
        }
    }

    private void drawBar(Canvas canvas) {
        drawBar(canvas, PlayerId.FIRST);
        drawBar(canvas, PlayerId.SECOND);
    }

    private void drawBar(Canvas canvas, PlayerId playerId) {
        ITable table = gameActivity.getGameModel().getGame().table();
        int numOfCheckers = playerId == PlayerId.FIRST ? table.getPlayerOneBar() : table.getPlayerTwoBar();

        if (numOfCheckers > 0) {
            BoardFeatures boardFeatures = gameActivity.getSettings().getBoardFeatures();
            int widthOfOne = (int)(width * boardFeatures.getCheckerWidth());
            int heightOfOne = (int)(height * boardFeatures.getCheckerHeight());

            Point point = GeometryUtility.barPointFromPlayerId(playerId, width, height, boardFeatures);
            Bitmap bitmap = playerId == PlayerId.FIRST ? player1 : player2;
            canvas.drawBitmap(bitmap, point.x, point.y, null);

            point.x += widthOfOne / 2;
            point.y += heightOfOne;

            textPaint.setTextSize(heightOfOne);
            canvas.drawText(numOfCheckers + "", point.x, point.y, textPaint);
        }
    }

    private void drawCurrentChecker(Canvas canvas) {
        Point point = gameActivity.getController().getCurrentChecker();
        if (point == null) return;

        PlayerId playerId = gameActivity.getGameModel().getCurrentPlayer();
        Bitmap bitmap = playerId == PlayerId.FIRST ? player1 : player2;
        BoardFeatures boardFeatures = gameActivity.getSettings().getBoardFeatures();

        int width = (int)(getWidth() * boardFeatures.getCheckerWidth());
        int height = (int)(getHeight() * boardFeatures.getCheckerHeight());

        canvas.drawBitmap(bitmap, point.x - width / 2, point.y - height / 2, null);
    }
}
