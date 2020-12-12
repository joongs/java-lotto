package lottery.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class LotteryResult {
    private Map<LotteryValue, Integer> lotteryResultMap;

    public LotteryResult() {
        lotteryResultMap = new LinkedHashMap<LotteryValue, Integer>(){{
            put(LotteryValue.FIFTH_PLACE, 0);
            put(LotteryValue.FORTH_PLACE, 0);
            put(LotteryValue.THIRD_PLACE, 0);
            put(LotteryValue.SECOND_PLACE, 0);
            put(LotteryValue.FIRST_PLACE, 0);
        }};
    }

    public Map<LotteryValue, Integer> getLotteryResultMap() {
        return this.lotteryResultMap;
    }

    public BigDecimal getProfit(int purchaseAmount) {
        int profit = 0;
        for (LotteryValue key : lotteryResultMap.keySet()) {
            profit += (lotteryResultMap.get(key) * key.getAmount());
        }
        return new BigDecimal(profit).divide(new BigDecimal(purchaseAmount), 3, RoundingMode.HALF_EVEN);
    }

    protected void updateLotteryResult(int key, boolean isMatchedBonusNumber) {
        LotteryValue resultKey = LotteryValue.findByAmount(key);
        if (isSecondPlace(key, isMatchedBonusNumber)) {
            resultKey = LotteryValue.SECOND_PLACE;
        }
        if (resultKey.equals(LotteryValue.MISS)) {
            return;
        }
        this.lotteryResultMap.replace(resultKey, lotteryResultMap.get(resultKey) + 1);
    }

    private boolean isSecondPlace(int key, boolean isMatchedBonusNumber) {
        return (key == LotteryValue.THIRD_PLACE.getPlace() && isMatchedBonusNumber);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotteryResult that = (LotteryResult) o;
        return Objects.equals(lotteryResultMap, that.lotteryResultMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lotteryResultMap);
    }
}
