package test.business.domain.business.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class PerLogger {
    private long startMillis;
    private long startNano;

    // 측정 시작
    public void start() {
        startMillis = System.currentTimeMillis();
        startNano = System.nanoTime();
    }

    // 측정 종료 및 로그 출력 (context 메시지 포함)
    public void endAndLog(String context) {
        long endMillis = System.currentTimeMillis();
        long endNano = System.nanoTime();

        long elapsedMillis = endMillis - startMillis;
        long elapsedNano = endNano - startNano;

        log.info("[Performance] {} - 시작: {} ms, 종료: {} ms, 소요 시간: {} ms ({} ns)",
                context, startMillis, endMillis, elapsedMillis, elapsedNano);
    }

}
