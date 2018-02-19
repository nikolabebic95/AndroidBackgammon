package rs.ac.bg.etf.pmu.bn140314d.backgammon.gui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import rs.ac.bg.etf.pmu.bn140314d.backgammon.R;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.gui.helpers.DateHelper;
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.db.GameTable;

public class GameHistoryAdapter extends ArrayAdapter<GameTable> {
    private List<GameTable> gameTables;
    private Activity context;

    public GameHistoryAdapter(@NonNull Activity context, List<GameTable> gameTables) {
        super(context, R.layout.game_history_row, gameTables);
        this.gameTables = gameTables;
        this.context = context;
    }

    @SuppressLint({"SetTextI18n", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.game_history_row, null, true);
        }

        TextView player1Name = rowView.findViewById(R.id.player_1_name);
        TextView player2Name = rowView.findViewById(R.id.player_2_name);
        TextView player1Points = rowView.findViewById(R.id.player_1_points);
        TextView player2Points = rowView.findViewById(R.id.player_2_points);
        TextView timestamp = rowView.findViewById(R.id.timestamp);

        GameTable gameTable = gameTables.get(position);

        player1Name.setText(gameTable.getPlayerOneName());
        player2Name.setText(gameTable.getPlayerTwoName());
        player1Points.setText(Integer.toString(gameTable.getPlayerOnePoints()));
        player2Points.setText(Integer.toString(gameTable.getPlayerTwoPoints()));
        timestamp.setText(DateHelper.getDateCurrentTimeZone(gameTable.getGameTimestamp()));

        return rowView;
    }
}
