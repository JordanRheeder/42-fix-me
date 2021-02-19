package wtc.fixme.market.Instruments;

import wtc.fixme.market.Market;
import wtc.fixme.market.Instruments.Stonks;

public class StockMarket extends Market {
    public StockMarket() {
        stonks.add(new Stonks("GME", "Game Stop"));
        stonks.add(new Stonks("TSA", "Tesla"));
        stonks.add(new Stonks("AMZ", "Amazon"));
        stonks.add(new Stonks("APL", "Apple"));
        stonks.add(new Stonks("MS", "Microsoft"));
        stonks.add(new Stonks("NFX", "Netflix"));
    }
}
