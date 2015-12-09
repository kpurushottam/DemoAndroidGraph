package com.krp.android.demoandroidgraph;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.BaseSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {

    private GraphView graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graph = (GraphView) findViewById(R.id.graph);

        /**
         * GraphView has changed some basics so the first important change is that
         * the type of the chart is defined through the series class that you are using.
         * You always use the class GraphView and add the Series subclasses.
         */
        PointsGraphSeries<DataPoint> series = new PointsGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });
        series.setSize(12f);
        graph.addSeries(series);

        /**
         * If you have used static labels, you can use this in GraphView 4.0 in another way.
         * You have to set a static label formatter.
         Step #2a: Migrate static labels
         */
        /*StaticLabelsFormatter labelsFormatter = new StaticLabelsFormatter(graph);
        labelsFormatter.setHorizontalLabels(new String[] {"I", "II", "III", "IV", "V"});
        labelsFormatter.setVerticalLabels(new String[] {"100", "200", "300", "400", "500"});

        graph.getGridLabelRenderer().setLabelFormatter(labelsFormatter);*/


        /**
         * If you are using the build-in label generation (dynamic labels)
         * you can customize them with a own implementation of LabelFormatter. This is very similar to GraphView 3.x.
         Step #2b: Migrate custom label formatter
         */
        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    // show normal x values
                    return super.formatLabel(value, isValueX);
                } else {
                    // show currency for y values
                    String IndianRupee = "";
                    try {
                        IndianRupee = new String("\u20B9".getBytes(),"UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    return super.formatLabel(value, isValueX) + IndianRupee;
                }
            }
        });


        /**
         * If you want to define explicit bounds of the viewport,
         * you have to set these properties to the viewport object inside the GraphView object.
         Step #3: Viewport properties
         */
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(5);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(0);
        graph.getViewport().setMaxY(10);



        graph.getViewport().setScrollable(true);



        graph.setTitle("foo");
    }
}
