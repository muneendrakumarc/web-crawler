package com.pramati;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class FileSpider {

	public static void main(String[] args) throws IOException {

		int j = 0;
		String url = Constants.URL;
		Document document = Jsoup.connect(url).get();
		Document document1 = null;
		Elements tables = document.getElementsByTag(Constants.TABLE);

		for (Element table : tables) {

			if (table.className().contains(Constants.YEAR)) {
				
				Elements elements = table.getElementsByTag("td");
				
				for (Element subEle : elements) {
					if (subEle.text().contains(Constants.Y2014)) {

						Elements links = subEle.nextElementSibling().select(
								Constants.HREF);

						for (Element link : links) {
							String value = link.attr(Constants.ABSHREF);
							if (value.contains(Constants.DATE))
								document = Jsoup.connect(value).get();

						}// for

						Elements tables1 = document
								.getElementsByTag(Constants.TABLE);

						for (Element table1 : tables1) {
							File dir = new File(
									"C:\\Users\\muneendrac\\files\\"
											+ subEle.text());
							dir.mkdir();
							if (table1.id().equals(Constants.MSGLIST)) {

								Elements a11 = table1
										.getElementsByTag(Constants.TD);
								for (Element td : a11) {

									if (td.className()
											.equals(Constants.SUBJECT)) {

										Elements linkss = td
												.select(Constants.HREF);

										for (Element link : linkss) {

											String value = link
													.attr(Constants.ABSHREF);
											document = Jsoup.connect(value)
													.get();
											Elements elementss = document
													.getElementsByTag(Constants.TD);
											StringBuffer data = new StringBuffer();
											for (Element element : elementss) {

												data.append(element.text());
												data.append(System
														.getProperty(Constants.LINESEPERATOR));

											}// for

											File file = new File(
													dir.getAbsolutePath()
															+ "\\filename"
															+ j++ + ".txt");
											FileWriter fw = new FileWriter(file);
											BufferedWriter bw = new BufferedWriter(
													fw);
											bw.write(data.toString());
											bw.close();

										}// for

									}// if

								}// for

								Elements headersLinks = table1
										.getElementsByTag(Constants.TH);
								for (Element headerLink : headersLinks) {

									Elements linkss = headerLink
											.select(Constants.HREF);

									for (Element links1 : linkss) {

										String value = links1
												.attr(Constants.ABSHREF);
										if (value.contains(Constants.DATE)) {

											document1 = Jsoup.connect(value)
													.get();

											Elements details = document1
													.getElementsByTag(Constants.TABLE);

											for (Element detail : details) {

												Elements innerValue = detail
														.getElementsByTag(Constants.TD);

												for (Element td : innerValue) {

													if (td.className().equals(
															Constants.SUBJECT)) {

														Elements tdlinks = td
																.select(Constants.HREF);

														for (Element tdlink : tdlinks) {

															String urlvalue = tdlink
																	.attr(Constants.ABSHREF);

															document1 = Jsoup
																	.connect(
																			urlvalue)
																	.get();

															Elements elements1 = document1
																	.getElementsByTag(Constants.TD);
															StringBuffer data = new StringBuffer();
															for (Element element : elements1) {

																data.append(element
																		.text());
																data.append(System
																		.getProperty(Constants.LINESEPERATOR));

															}// for

															File file = new File(
																	dir.getAbsolutePath()
																			+ "\\extra"
																			+ j++
																			+ ".txt");
															FileWriter fw = new FileWriter(
																	file);
															BufferedWriter bw = new BufferedWriter(
																	fw);
															bw.write(data
																	.toString());
															bw.close();

														}// for

													}// if
												}// for

											}// for

										}// if
									}// for

								}// for

							}// if

						}// for

					}// if
				}
			}// if

		}// for

	}// main

}// class