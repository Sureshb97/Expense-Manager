package com.example.expense_manager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class HomeFragment extends Fragment {
    TextView date;
    TextView food_value,entertainment_value,clothing_value,transportation_value,housing_value,others_value;
    LinearLayout field1,field2,field3,field4,field5,field6;
    LinearLayout active;
    String[] category;
    ImageView datepicker,refresh;
    Context context;
    PieChart chart;
    String selectedDate;
    
    public HomeFragment(Context context) {
        this.context = context;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_home, container, false);
        return  view;
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        datepicker=view.findViewById(R.id.calendar);
        refresh=view.findViewById(R.id.refresh);
        chart=view.findViewById(R.id.pie_chart);
        date=view.findViewById(R.id.date);
        food_value=view.findViewById(R.id.food_value);
        entertainment_value=view.findViewById(R.id.entertain_value);
        clothing_value=view.findViewById(R.id.clothing_value);
        transportation_value=view.findViewById(R.id.transportation_value);
        housing_value=view.findViewById(R.id.housing_value);
        others_value=view.findViewById(R.id.others_value);
        field1 =view.findViewById(R.id.field1);
        field2 =view.findViewById(R.id.field2);
        field3 =view.findViewById(R.id.field3);
        field4 =view.findViewById(R.id.field4);
        field5 =view.findViewById(R.id.field5);
        field6 =view.findViewById(R.id.field6);
        
        selectedDate=String.valueOf(new Date(System.currentTimeMillis()));
        date.setText(selectedDate);
        get(Date.valueOf(selectedDate));

        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar=Calendar.getInstance(TimeZone.getDefault());
                DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        int selectedMonth=month+1;
                        String dateSet=year+"-"+selectedMonth+"-"+dayOfMonth;
                        date.setText(dateSet);
                        get(Date.valueOf(dateSet));
                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show();
            }
        });
        
        refresh.setClickable(true);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get(new Date(System.currentTimeMillis()));
                date.setText(selectedDate);
            }
        });
    }
    
    public void chartDataAssigning(float[] dat, final float total)
    {
        float [] data;
        data=dat;
        int PaleGreen=getResources().getColor(R.color.PaleGreen,getActivity().getTheme());
        int Salmon =getResources().getColor(R.color.Salmon,getActivity().getTheme());
        int Plum=getResources().getColor(R.color.Plum,getActivity().getTheme());
        int SandyBrown=getResources().getColor(R.color.SandyBrown,getActivity().getTheme());
        int LightBlue=getResources().getColor(R.color.LightBlue,getActivity().getTheme());
        int Khaki=getResources().getColor(R.color.Khaki,getActivity().getTheme());
        int[] colors={PaleGreen,Salmon,Plum,SandyBrown,LightBlue,Khaki};
        String centerText="₹ "+String.valueOf(total);
        ArrayList<PieEntry> pieEntries=new ArrayList<PieEntry>();
        ArrayList<Integer> col=new ArrayList<Integer>();
        category= new String[]{"Food", "Entertainment","Clothing","Transportation","Housing","Others"};

        for(int i=0;i<dat.length;i++)
        {
            if(data[i]>0)
            {
                pieEntries.add(new PieEntry(data[i],category[i]));
                col.add(colors[i]);
            }
        }

        final PieDataSet dataSet=new PieDataSet(pieEntries,"");
        final PieData pieData=new PieData(dataSet);

        dataSet.setColors(col);
        dataSet.setSliceSpace(1);
        dataSet.setValueTextColor(getResources().getColor(R.color.Black,getActivity().getTheme()));
        dataSet.setValueTextSize(15.0f);
        dataSet.setHighlightEnabled(true);

        pieData.setValueFormatter(new PercentFormatter(chart));
        Legend legend=chart.getLegend();
        legend.setTextColor(getResources().getColor(android.R.color.tab_indicator_text,getActivity().getTheme()));

        chart.setData(pieData);
        chart.setCenterTextSize(15);
        chart.invalidate();
        chart.setDrawEntryLabels(false);
        chart.setHoleRadius(35);
        chart.setCenterText(centerText);
        chart.setUsePercentValues(true);
        chart.setEntryLabelColor(getResources().getColor(R.color.Black,getActivity().getTheme()));
        chart.setHighlightPerTapEnabled(true);
        chart.getDescription().setEnabled(false);
        chart.animateY(1000, Easing.EaseInOutCubic);
        
        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                PieEntry pieEntry = (PieEntry) e;
                String name = pieEntry.getLabel();
                    switch (name) {
                        case "Food":
                            if (active == null) {
                                field1.setBackground(ContextCompat.getDrawable(context, R.drawable.food_border));
                                active = field1;
                            }
                            active.setBackgroundColor(getResources().getColor(R.color.blank, getActivity().getTheme()));
                            field1.setBackground(ContextCompat.getDrawable(context, R.drawable.food_border));
                            active = field1;
                            break;
                        case "Entertainment":
                            if (active == null) {
                                field2.setBackground(ContextCompat.getDrawable(context, R.drawable.entertainment_border));
                                active = field2;
                            }
                            active.setBackgroundColor(getResources().getColor(R.color.blank, getActivity().getTheme()));
                            field2.setBackground(ContextCompat.getDrawable(context, R.drawable.entertainment_border));
                            active = field2;
                            break;
                        case "Housing":
                            if (active == null) {
                                field3.setBackground(ContextCompat.getDrawable(context, R.drawable.housing_border));
                                active = field3;
                            }
                            active.setBackgroundColor(getResources().getColor(R.color.blank, getActivity().getTheme()));
                            field3.setBackground(ContextCompat.getDrawable(context, R.drawable.housing_border));
                            active = field3;
                            break;
                        case "Clothing":
                            if (active == null) {
                                field4.setBackground(ContextCompat.getDrawable(context, R.drawable.clothing_border));
                                active = field4;
                            }
                            active.setBackgroundColor(getResources().getColor(R.color.blank, getActivity().getTheme()));
                            field4.setBackground(ContextCompat.getDrawable(context, R.drawable.clothing_border));
                            active = field4;
                            break;
                        case "Transportation":
                            if (active == null) {
                                field5.setBackground(ContextCompat.getDrawable(context, R.drawable.transportation_border));
                                active = field5;
                            }
                            active.setBackgroundColor(getResources().getColor(R.color.blank, getActivity().getTheme()));
                            field5.setBackground(ContextCompat.getDrawable(context, R.drawable.transportation_border));
                            active = field5;
                            break;
                        case "Others":
                            if (active == null) {
                                field6.setBackground(ContextCompat.getDrawable(context, R.drawable.others_border));
                                active = field6;
                            }
                            active.setBackgroundColor(getResources().getColor(R.color.blank, getActivity().getTheme()));
                            field6.setBackground(ContextCompat.getDrawable(context, R.drawable.others_border));
                            active = field6;
                            break;
                    }
            }
            
            @Override
            public void onNothingSelected() {
                field1.setBackgroundColor(getResources().getColor(R.color.blank,getActivity().getTheme()));
                field2.setBackgroundColor(getResources().getColor(R.color.blank,getActivity().getTheme()));
                field3.setBackgroundColor(getResources().getColor(R.color.blank,getActivity().getTheme()));
                field4.setBackgroundColor(getResources().getColor(R.color.blank,getActivity().getTheme()));
                field5.setBackgroundColor(getResources().getColor(R.color.blank,getActivity().getTheme()));
                field6.setBackgroundColor(getResources().getColor(R.color.blank,getActivity().getTheme()));
            }
        });
    }
    
    public void amountDataAssigning(float[] amount)
    {
        food_value.setText("₹ "+String.valueOf((int)amount[0]));
        entertainment_value.setText("₹ "+String.valueOf((int)amount[1]));
        clothing_value.setText("₹ "+String.valueOf((int)amount[2]));
        transportation_value.setText("₹ "+String.valueOf((int) amount[3]));
        housing_value.setText("₹ "+String.valueOf((int)amount[4]));
        others_value.setText("₹ "+String.valueOf((int)amount[5]));
    }

    public void get(final Date date) 
    {
        
        class Getdata extends AsyncTask<Void, Void, float[]> 
        {
            String FOOD = "Food";
            String ENTERTAINMENT = "Entertainment";
            String CLOTHING = "Clothing";
            String TRANSPORTATION = "Transportation";
            String HOUSING = "Housing";
            String OTHERS = "Others";
            Date selectedDate=date;
            float total;
            float[] percentage;
            float[] amount;
            Context ctx = getContext();
            @Override
            protected float[] doInBackground(Void... voids) {
                float foodSum = DatabaseClient.getInstance(ctx).getExpenseDatabase().expenseDao().getSumQuery(selectedDate, FOOD);
                float entertainmentSum = DatabaseClient.getInstance(ctx).getExpenseDatabase().expenseDao().getSumQuery(selectedDate, ENTERTAINMENT);
                float clothingSum = DatabaseClient.getInstance(ctx).getExpenseDatabase().expenseDao().getSumQuery(selectedDate, CLOTHING);
                float transportationSum = DatabaseClient.getInstance(ctx).getExpenseDatabase().expenseDao().getSumQuery(selectedDate, TRANSPORTATION);
                float housingSum = DatabaseClient.getInstance(ctx).getExpenseDatabase().expenseDao().getSumQuery(selectedDate, HOUSING);
                float othersSum = DatabaseClient.getInstance(ctx).getExpenseDatabase().expenseDao().getSumQuery(selectedDate, OTHERS);
    
                total = foodSum + entertainmentSum + clothingSum + transportationSum + housingSum + othersSum;
    
                float foodAverage = (foodSum / total) * 100;
                float entertainmentAverage = (entertainmentSum / total) * 100;
                float clothingAverage = (clothingSum / total) * 100;
                float transportationAverage = (transportationSum / total) * 100;
                float housingAverage = (housingSum / total) * 100;
                float othersAverage = (othersSum / total) * 100;
                
                amount = new float[]{foodSum, entertainmentSum, clothingSum, transportationSum, housingSum, othersSum};
                percentage = new float[]{foodAverage, entertainmentAverage, clothingAverage, transportationAverage, housingAverage, othersAverage};
                return percentage;
            }
    
            @Override
            protected void onPostExecute(float[] aVoid) {
                super.onPostExecute(aVoid);
                chartDataAssigning(percentage, total);
                amountDataAssigning(amount);
            }
        } 
    
            Getdata getdata=new Getdata();
             getdata.execute();
    }
}