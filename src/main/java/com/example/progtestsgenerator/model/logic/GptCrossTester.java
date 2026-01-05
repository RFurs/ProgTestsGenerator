package com.example.progtestsgenerator.model.logic;

import com.example.progtestsgenerator.model.util.GptApiClient;

public class GptCrossTester extends CrossTester {

    private final GptApiClient apiClient;

    public GptCrossTester(GptApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Override
    public String testClassification(String prompt, Integer level) {
        String sysPrompt = buildClassificationPrompt(level);
        return removeMarkdowns(apiClient.generate(sysPrompt, prompt));
    }

    @Override
    public String testCorrectness(String prompt) {
        String sysPrompt = buildCorrectnessPrompt();
        return removeMarkdowns(apiClient.generate(sysPrompt, prompt));
    }

    private String buildClassificationPrompt(int level) {
        return """
            Tu tikrini klausimus pagal Bloom taksonomiją.
            Užduotys, kuriose studentas turi savarankiškai konstruoti sprendimą,
            laikomos 6 lygio.
            Jei visi atitinka %d lygį – grąžink n/n, kur n yra klausimų skaičius.
            Kitu atveju – nurodyk neatitinkančius ir parašyk kuriam lygiui iš tikrųjų priklauso.
            
            Išvesties formatas:
            m/n
            Klausimas pavadinimu ,,X`` neatitinka. Priklauso Y lygiui.
            """.formatted(level);
    }

    private String buildCorrectnessPrompt() {
        return """
            Tavo užduotis – patikrinti, ar pateikti sprendimų pavyzdžiai yra teisingi.

            Jei visi teisingi – n/n, kur n yra klausimų skaičius.
            Jei ne – nurodyk neteisingus ir pateik teisingą atsakymą.

            Pastaba:
            Moodle essay tipo klausimams tikrinti reikia pavyzdžius.

            Išvesties formatas:
            m/n
            Klausimas pavadiniu ,,X`` neteisingas. Teisingas atsakymas yra Y
            """;
    }
}
