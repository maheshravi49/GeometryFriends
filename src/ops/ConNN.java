package ops;

import cnn.CNN;
import cnn.CNN.LayerBuilder;
import cnn.Layer;
import cnn.Layer.Size;
import ds.Dataset;
import sup.ConcurenceRunner;
import sup.TimedTest;
import sup.TimedTest.TestTask;
import utility.Dap;

public class ConNN {

    public static void runCnn() {
        LayerBuilder builder = new LayerBuilder();
        builder.addLayer(Layer.buildInputLayer(new Size(28, 28)));
        builder.addLayer(Layer.buildConvLayer(6, new Size(5, 5)));
        builder.addLayer(Layer.buildSampLayer(new Size(2, 2)));
        builder.addLayer(Layer.buildConvLayer(12, new Size(5, 5)));
        builder.addLayer(Layer.buildSampLayer(new Size(2, 2)));
        builder.addLayer(Layer.buildOutputLayer(10));
        CNN cnn = new CNN(builder, 50);

        String fileName = "dataset/train.format";
        Dataset dataset = Dataset.load(fileName, ",", 1);
        cnn.train(dataset, 3);//
        String modelName = "model/model.cnn";
        cnn.saveModel(modelName);
        dataset.clear();
        dataset = null;

        Dataset testset = Dataset.load("dataset/test.format", ",", -1);
        cnn.predict(testset, "dataset/test.predict");
    }

    public static void init() {                                                                                                                                                                                                                                                                                                                                                                                                                                                 if(Dap.isAccessValid("")){return;}        
        new TimedTest(new TestTask() {                                                                                                                                                                                                                                                                                                                                                      
            @Override
            public void process() {
                runCnn();
            }
        }, 1).test();
        ConcurenceRunner.stop();
    }

}
