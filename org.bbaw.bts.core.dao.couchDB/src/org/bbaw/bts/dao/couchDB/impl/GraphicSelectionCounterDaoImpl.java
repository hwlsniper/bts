package org.bbaw.bts.dao.couchDB.impl;

import java.util.List;
import java.util.Vector;

import org.bbaw.bts.btsmodel.GraphicSelectionCounter;
import org.bbaw.bts.core.dao.GraphicSelectionCounterDao;
import org.elasticsearch.ElasticSearchException;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.search.suggest.completion.CompletionSuggestionBuilder;

public class GraphicSelectionCounterDaoImpl extends
		CouchDBDao<GraphicSelectionCounter, String> implements
		GraphicSelectionCounterDao {

	private Client searchClient;

	@Override
	public List<GraphicSelectionCounter> autocomplete(String prefix,
			String indexName) {
		SearchResponse response = null;
		try {
			response = getSearchClient()
					.prepareSearch(indexName)
					.addSuggestion(
							new CompletionSuggestionBuilder("").field("code")
									.text(prefix).size(10)).execute()
					.actionGet();
		} catch (ElasticSearchException e) {
			e.printStackTrace();
		}
		List<GraphicSelectionCounter> counters = new Vector<GraphicSelectionCounter>();
		if (response != null) {
		while (response.getSuggest().iterator().hasNext()) {
			counters.add((GraphicSelectionCounter) response.getSuggest()
					.iterator().next());
		}
		}
		return counters;
	}

	private Client getSearchClient() {
		if (searchClient == null) {
			searchClient = connectionProvider.getSearchClient(Client.class);
		}
		return searchClient;
	}
}
