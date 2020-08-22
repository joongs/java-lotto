package lotto.domain;

import java.util.*;

public class LottoResult {

    private static final int RATE = 100;

    private Map<WinningPrize, Integer> lottoPrizeResult = new LinkedHashMap<>();

    public LottoResult() {
        Arrays.stream(WinningPrize.values())
                .forEach(winningPrize -> this.lottoPrizeResult.put(winningPrize, 0));
    }

    public static LottoResult getInstance() {
        return new LottoResult();
    }

    public int calculatePrizeRate(int money) {
        return lottoPrizeResult.entrySet().stream()
                .mapToInt(result -> result.getKey().getPrize() * result.getValue()).sum() / money * RATE;
    }

    public void analyzeLottoRank(Set<LottoTicket> lottoTickets, WinningLotto winningLotto) {
        lottoTickets.stream()
                .map(lottoTicket -> countMatchNumber(lottoTicket, winningLotto))
                .forEach(winningPrize -> recordWinningResult(winningPrize));
    }

    private WinningPrize countMatchNumber(LottoTicket lottoTicket, WinningLotto winningLotto) {
        int matchCount = lottoTicket.countMatchNumbers(winningLotto.getWinningLottoNumber());
        WinningPrize winningPrize = WinningPrize.valueOf(matchCount);
        return winningPrize;
    }

    private void recordWinningResult(WinningPrize winningValue) {
        lottoPrizeResult.put(winningValue, lottoPrizeResult.get(winningValue) + 1);
    }

    public Map<WinningPrize, Integer> getLottoResult() {
        return Collections.unmodifiableMap(lottoPrizeResult);
    }
}
