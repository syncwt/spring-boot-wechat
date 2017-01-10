package com.senthink.www.domain.po.wechat;

import java.util.List;

/**
 *
 * @Project : Columbia
 * @Package Name  : com.senthink.www.quartz
 * @Description :  微信推送消息
 * @Author : wanwt@senthink.com
 * @Creation Date : 2016年12月15日 下午5:35
 * @ModificationHistory Who        When          What
 * --------   ----------    -----------------------------------
 */
public class NewsMessage extends BaseMessage 
{
	// 图文消息个数，限制为10条以内
	private int ArticleCount;
	// 多条图文消息信息，默认第一个item为大图
	private List<Article> Articles;

	public int getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(int articleCount) {
		ArticleCount = articleCount;
	}

	public List<Article> getArticles() {
		return Articles;
	}

	public void setArticles(List<Article> articles) {
		Articles = articles;
	}
}
