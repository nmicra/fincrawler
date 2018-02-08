package il.fincrawl.fincrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Ignore;
import org.junit.Test;
import org.quartz.TimeOfDay;

import java.time.LocalDate;
import java.util.Optional;
import java.util.StringTokenizer;

@Ignore
public class FetchDataTest {

    @Test
    public void usDebt() throws Exception {
//        String blogUrl = "https://www.treasurydirect.gov/NP/debt/search?startMonth=01&startDay=01&startYear=2018&endMonth=01&endDay=10&endYear=2018";
        String blogUrl = "https://www.treasurydirect.gov/NP/debt/current";
        Document doc = Jsoup.connect(blogUrl).userAgent("Mozilla")
                .timeout(10000).get();
        Elements data1 = doc.getElementsByClass("data1");
        String text = data1.select("tr").last().select("td").last().text();
        System.out.println(text);
        System.out.println(text.replaceAll("[^0-9\\.]",""));
    }


    @Test
    public void goldPrice() throws Exception {
        // For 2016 year use the following link
//        String blogUrl = "http://www.usagold.com/http://www.usagold.com/reference/goldprices/2016.html";



        String blogUrl = "http://www.usagold.com/";
        Document doc = Jsoup.connect(blogUrl).userAgent("Mozilla")
                .timeout(10000).get();
        Optional<Element> first = doc.select("span").stream().filter(e -> e.hasClass("arial10")).findFirst();
        String gold = first.get().text().replaceAll("\\$", "").replaceAll("\\,", "").replace("Gold", "").trim();
        StringTokenizer st = new StringTokenizer(gold);
        System.out.println("goldprice=" + st.nextToken());
        System.out.println("delta=" + st.nextToken());
    }


    @Test
    public void fearIndex() throws Exception {

        String blogUrl = "https://www.investing.com/indices/volatility-s-p-500";
        Document doc = Jsoup.connect(blogUrl).userAgent("Mozilla")
                .timeout(10000).get();
        Optional<Element> div = doc.body().select("div").stream().filter(e -> e.hasClass("top bold inlineblock")).findFirst();
        System.out.println("vix index =" + div.get().selectFirst("span").text());
        System.out.println("date=" + LocalDate.now());
    }
}
