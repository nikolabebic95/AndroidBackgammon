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
import rs.ac.bg.etf.pmu.bn140314d.backgammon.persistence.db.PairTable;

public class HallOfFameAdapter extends ArrayAdapter<PairTable> {
    private List<PairTable> pairTables;
    private Activity context;

    public HallOfFameAdapter(@NonNull Activity context, List<PairTable> pairTables) {
        super(context, R.layout.hall_of_fame_row, pairTables);
        this.pairTables = pairTables;
        this.context = context;
    }

    @SuppressLint({"InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            rowView = inflater.inflate(R.layout.hall_of_fame_row, null, true);
        }

        TextView player1Name = rowView.findViewById(R.id.player_1_name);
        TextView player2Name = rowView.findViewById(R.id.player_2_name);
        TextView player1Points = rowView.findViewById(R.id.player_1_points);
        TextView player2Points = rowView.findViewById(R.id.player_2_points);

        PairTable pairTable = pairTables.get(position);

        player1Name.setText(pairTable.getPlayerOneName());
        player2Name.setText(pairTable.getPlayerTwoName());
        player1Points.setText(Integer.toString(pairTable.getPlayerOneVictories()));
        player2Points.setText(Integer.toString(pairTable.getPlayerTwoVictories()));

        return rowView;
    }
}
