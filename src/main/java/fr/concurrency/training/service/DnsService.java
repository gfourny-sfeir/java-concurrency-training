package fr.concurrency.training.service;

import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import fr.concurrency.training.model.dns.Dns;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gfourny
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class DnsService {

    private static final String DNS_1 = "dns1";
    private static final String DNS_2 = "dns2";
    private static final String DNS_3 = "dns3";

    private final Apis apis;

    /**
     * <b>TODO: paralléliser les appels et ne retenir que le dns le plus rapide</b>
     *
     * @return {@link Dns}
     */
    public Dns obtainFastestDns() {

        var stopWatch = new StopWatch("dns");

        stopWatch.start("fetchDns-1");
        var firstDns = apis.fetchDns(DNS_1);
        stopWatch.stop();

        var totalTimeMillisFirstDns = stopWatch.lastTaskInfo().getTimeMillis();

        stopWatch.start("fetchDns-2");
        var secondDns = apis.fetchDns(DNS_2);
        stopWatch.stop();
        var totalTimeMillisSecondDns = stopWatch.lastTaskInfo().getTimeMillis();

        stopWatch.start("fetchDns-3");
        Dns thirdDns = apis.fetchDns(DNS_3);
        stopWatch.stop();
        var totalTimeMillisThirdDns = stopWatch.lastTaskInfo().getTimeMillis();

        if (totalTimeMillisFirstDns <= totalTimeMillisSecondDns && totalTimeMillisFirstDns <= totalTimeMillisThirdDns) {
            return firstDns;
        } else if (totalTimeMillisSecondDns <= totalTimeMillisFirstDns && totalTimeMillisSecondDns <= totalTimeMillisThirdDns) {
            return secondDns;
        } else {
            return thirdDns;
        }
    }
}
