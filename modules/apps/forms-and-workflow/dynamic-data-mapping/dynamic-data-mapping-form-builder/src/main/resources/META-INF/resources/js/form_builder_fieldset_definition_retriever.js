AUI.add(
	'liferay-ddm-form-builder-fieldset-definition-retriever',
	function(A) {
		var CACHE = {};

		var FormBuilderFieldsetDefinitionRetriever = A.Component.create(
			{
				EXTENDS: A.Base,

				NAME: 'liferay-ddm-form-builder-fieldset-definition-retriever',

				prototype: {
					getDefinition: function(fieldSet) {
						var instance = this;

						return new A.Promise(
							function(resolve, reject) {
								var resolveJSON = function(json) {
									var parsed = JSON.parse(json);

									resolve(parsed);

									return parsed;
								};

								var id = fieldSet.get('id');

								var cachedDefinitionJSON = CACHE[id];

								if (cachedDefinitionJSON) {
									resolveJSON(cachedDefinitionJSON);
								}
								else {
									var payload = {
										bcp47LanguageId: themeDisplay.getBCP47LanguageId(),
										ddmStructureId: id,
										portletNamespace: Liferay.DDM.Settings.portletNamespace,
										scopeGroupId: themeDisplay.getScopeGroupId()
									};

									A.io.request(
										Liferay.DDM.Settings.fieldSetDefinitionURL,
										{
											data: payload,
											dataType: 'JSON',
											method: 'GET',
											on: {
												failure: function(error) {
													reject(error);
												},
												success: function(event, status, xhr) {
													var definitionJSON = xhr.responseText;

													CACHE[id] = definitionJSON;

													resolveJSON(definitionJSON);
												}
											}
										}
									);
								}
							}
						);
					}
				}
			}
		);

		Liferay.namespace('DDM').FormBuilderFieldsetDefinitionRetriever = FormBuilderFieldsetDefinitionRetriever;
	},
	'',
	{
		requires: ['aui-promise', 'aui-request']
	}
);