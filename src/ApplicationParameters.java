import java.util.Optional;

public class ApplicationParameters {

    public static int NUMBER_OF_QUEUES;
    public static int NUMBER_OF_SERVERS;

    public static InputDataType INPUT_DATA_TYPE;

    public static String LOG_FILE_NAME;
    public static String RESULT_FILE_NAME;

    public static int NUMBER_OF_GENERATIONS = 200;
    public static String INPUT_STATIC_FILE;
    public static String INPUT_ARRIVAL_TIME_FILE;
    public static String INPUT_TIME_IT_TAKES_FILE;

    public ApplicationParameters() {
        NUMBER_OF_QUEUES = Optional.ofNullable(System.getenv("NUMBER_OF_QUEUES"))
                .map(Integer::valueOf)
                .orElse(1);
        NUMBER_OF_SERVERS = Optional.ofNullable(System.getenv("NUMBER_OF_SERVERS"))
                .map(Integer::valueOf)
                .orElse(2);
        INPUT_DATA_TYPE = Optional.ofNullable(System.getenv("INPUT_DATA_TYPE"))
                .map(InputDataType::valueOf)
                .orElse(InputDataType.DISTRIBUITION);

        LOG_FILE_NAME = Optional.ofNullable(System.getenv("LOG_FILE_NAME"))
                .orElse("executionLog.txt");
        RESULT_FILE_NAME = Optional.ofNullable(System.getenv("RESULT_FILE"))
                .orElse("resultMetric.txt");

        NUMBER_OF_GENERATIONS = Optional.ofNullable(System.getenv("NUMBER_OF_GENERATIONS"))
                .map(Integer::valueOf)
                .orElse(200);
        INPUT_STATIC_FILE = Optional.ofNullable(System.getenv("INPUT_STATIC_FILE"))
                .orElse("dados_trabalho.csv");
        INPUT_ARRIVAL_TIME_FILE = Optional.ofNullable(System.getenv("INPUT_ARRIVAL_TIME_FILE"))
                .orElse("tempo_chegada_distribuicao.csv");
        INPUT_TIME_IT_TAKES_FILE = Optional.ofNullable(System.getenv("INPUT_TIME_IT_TAKES_FILE"))
                .orElse("tempo_servico_distribuicao.csv");
    }
}
