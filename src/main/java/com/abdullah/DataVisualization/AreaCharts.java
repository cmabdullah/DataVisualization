package com.abdullah.DataVisualization;
import java.awt.*;
import java.time.LocalDate;
import java.util.stream.Stream;

import com.zavtech.morpheus.array.Array;
import com.zavtech.morpheus.frame.DataFrame;
import com.zavtech.morpheus.range.Range;
import com.zavtech.morpheus.viz.chart.Chart;
public class AreaCharts {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Hii cm");
		   final DataFrame<String,String> frame = createDataset();
	        frame.out().print();
  
	        int rowCount = 100;
	        Range<Integer> rowKeys = Range.of(0, rowCount);
	        DataFrame<Integer,String> frame1 = DataFrame.of(rowKeys, String.class, columns -> {
	            Stream.of("A", "B", "C", "D", "E").forEach(label -> {
	                columns.add(label, Array.randn(rowCount, 10d, 100d).cumSum());
	            });
	        });

	        Chart.create().withAreaPlot(frame1, true, chart -> {
	            chart.plot().axes().domain().label().withText("X-Value");
	            chart.plot().axes().range(0).label().withText("Random Value");
	            chart.title().withText("Stacked Area Chart");
	            chart.subtitle().withText("Cumulative Sum of Random Normal Data");
	            chart.legend().on();
	            chart.show();
	        });
	
	}
    private static DataFrame<String,String> createDataset() {
        final Array<String> years = Array.of("1980", "1985", "1990", "1995", "2000", "2005", "2010");
        final DataFrame<String,String> frame = DataFrame.read().csv(options -> {
            options.setResource("gdp_per_capita.csv");
            options.setRowKeyParser(String.class, values -> values[0]);
        });
        return frame.cols().select(years).rows().select(Array.of(
                "Brazil", "Germany", "Norway", "Singapore", "Sweden", "United Kingdom", "United States", "World"
        ));
    }
}
