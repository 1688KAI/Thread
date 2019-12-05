public enum FourSeasons_enum {



    spring(1, "春天", "风和日丽"),
    summer(2, "夏天", "晴空万里"),
    autumn(3, "秋天", "秋高气爽"),
    winter(4, "冬天", "白雪皑皑");

    private Integer code;
    private String seasons;
    private String description;

    FourSeasons_enum(Integer code, String seasons, String description) {
        this.code = code;
        this.seasons = seasons;
        this.description = description;
    }
    public Integer getCode() {
        return code;
    }
    public String getSeasons() {
        return seasons;
    }
    public String getDescription() {
        return description;
    }
    public static FourSeasons_enum findByCode(int code) {
        FourSeasons_enum[] seasons_enums = FourSeasons_enum.values();

        for (FourSeasons_enum element : seasons_enums) {
            if (code == element.getCode()) {
                return element;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 4; i++) {
            FourSeasons_enum seasons_enum = FourSeasons_enum.findByCode(i);
            System.out.println(seasons_enum.code);
            System.out.println(seasons_enum.seasons);
            System.out.println(seasons_enum.description);
        }
    }
}
