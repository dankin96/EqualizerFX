package ru.bmstu.www.equalizer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import ru.bmstu.www.filter.Filter;
import ru.bmstu.www.filter.coefs.FilterInfo;

public class Equalizer {

    private short[] inputSignal;
    private short[] outputSignal;
    private Filter[] filters;
    private final static int COUNT_OF_BANDS = 6;
    private final static char COUNT_OF_THREADS = 4;
    private final int lenghtOfInputSignal;
    private ExecutorService pool;

    public Equalizer(final int lenghtOfInputSignal) {

        pool = Executors.newFixedThreadPool(COUNT_OF_THREADS);
        this.lenghtOfInputSignal = lenghtOfInputSignal;
        this.createFilters();
    }

    public void setInputSignal(short[] inputSignal) {
        this.inputSignal = inputSignal;
        this.outputSignal = new short[this.lenghtOfInputSignal];
        this.filters[0].settings(FilterInfo.CoefsOfBand_0,
                FilterInfo.COUNT_OF_COEFS, this.inputSignal);
        this.filters[1].settings(FilterInfo.CoefsOfBand_1,
                FilterInfo.COUNT_OF_COEFS, this.inputSignal);
        this.filters[2].settings(FilterInfo.CoefsOfBand_2,
                FilterInfo.COUNT_OF_COEFS, this.inputSignal);
        this.filters[3].settings(FilterInfo.CoefsOfBand_3,
                FilterInfo.COUNT_OF_COEFS, this.inputSignal);
        this.filters[4].settings(FilterInfo.CoefsOfBand_4,
                FilterInfo.COUNT_OF_COEFS, this.inputSignal);
        this.filters[5].settings(FilterInfo.CoefsOfBand_5,
                FilterInfo.COUNT_OF_COEFS, this.inputSignal);

    }

    private void createFilters() {
        this.filters = new Filter[Equalizer.COUNT_OF_BANDS];
        this.filters[0] = new Filter(this.lenghtOfInputSignal);
        this.filters[1] = new Filter(this.lenghtOfInputSignal);
        this.filters[2] = new Filter(this.lenghtOfInputSignal);
        this.filters[3] = new Filter(this.lenghtOfInputSignal);
        this.filters[4] = new Filter(this.lenghtOfInputSignal);
        this.filters[5] = new Filter(this.lenghtOfInputSignal);
    }

    public void equalization() throws InterruptedException, ExecutionException {
        Future<short[]>[] fs = new Future[Equalizer.COUNT_OF_BANDS];
        for (int i = 0; i < Equalizer.COUNT_OF_BANDS; i++) {
            fs[i] = pool.submit(this.filters[i]);
        }

        for (int i = 0; i < this.outputSignal.length; i++) {
            this.outputSignal[i] += fs[0].get()[i]
                    + fs[1].get()[i]
                    + fs[2].get()[i]
                    + fs[3].get()[i]
                    + fs[4].get()[i]
                    + fs[5].get()[i];
        }
    }

    public Filter getFilter(short nF) {
        return this.filters[nF];
    }

    public short[] getOutputSignal() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return this.outputSignal;
    }

    public void close() {
        if (this.pool != null) {
            this.pool.shutdown();
        }
    }

}
