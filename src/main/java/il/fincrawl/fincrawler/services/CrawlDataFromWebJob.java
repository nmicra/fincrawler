package il.fincrawl.fincrawler.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.StringTokenizer;

@Service
public class CrawlDataFromWebJob implements Job {

    private final Logger errLogger = LoggerFactory.getLogger(CrawlDataFromWebJob.class);
    private final Logger log = LoggerFactory.getLogger("analytics");


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            goldPrice();
        } catch (Exception e) {
            errLogger.error(e.getMessage(), e);
        } try {
            usDebt();
        } catch (Exception e) {
            errLogger.error(e.getMessage(), e);
        } try {
            fearIndex();
        } catch (Exception e) {
            errLogger.error(e.getMessage(), e);
        }
    }

    private void goldPrice() throws Exception {
        String blogUrl = "http://www.usagold.com/";
        Document doc = Jsoup.connect(blogUrl).userAgent("Mozilla")
                .timeout(10000).get();
        Optional<Element> first = doc.select("span").stream().filter(e -> e.hasClass("arial10")).findFirst();
        String gold = first.get().text().replaceAll("\\$", "").replaceAll("\\,", "").replace("Gold", "").trim();
        StringTokenizer st = new StringTokenizer(gold);
        log.info(String.format("date=%s goldprice=%s delta=%s", LocalDate.now(), st.nextToken(), st.nextToken()));
    }

    private void usDebt() throws Exception {
        String blogUrl = "https://www.treasurydirect.gov/NP/debt/current";
        Document doc = Jsoup.connect(blogUrl).userAgent("Mozilla")
                .timeout(10000).get();
        Elements data1 = doc.getElementsByClass("data1");
        String text = data1.select("tr").last().select("td").last().text();
        log.info(String.format("date=%s usdebdt=%s", LocalDate.now(), text.replaceAll("[^0-9\\.]", "")));
    }

    private void fearIndex() throws Exception {
        String blogUrl = "https://www.investing.com/indices/volatility-s-p-500";
        Document doc = Jsoup.connect(blogUrl).userAgent("Mozilla")
                .timeout(10000).get();
        Optional<Element> div = doc.body().select("div").stream().filter(e -> e.hasClass("top bold inlineblock")).findFirst();
        log.info(String.format("date=%s vix=%s", LocalDate.now(), div.get().selectFirst("span").text()));
    }
}
