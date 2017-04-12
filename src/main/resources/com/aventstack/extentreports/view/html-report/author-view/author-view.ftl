<#if authorContext?? && authorContext?size != 0>
<div id='author-view' class='view hide'>
    <section id='controls'>
        <div class='controls grey lighten-4'>
            <!-- search -->
            <div class='chip transparent' alt='Search Authors' title='Search Authors'>
                <a href="#" class='search-div'>
                    <i class='material-icons'>search</i> Search
                </a>

                <div class='input-field left hide'>
                    <input id='search-tests' type='text' class='validate browser-default' placeholder='Search Authors...'>
                </div>

            </div>
            <!-- search -->
        </div>
    </section>

    <div class='subview-left left'>

        <div class='view-summary'>
            <h5>Authors</h5>
                <ul id='author-collection' class='author-collection'>

                    <#list authorContext as author>
                        <li class='author displayed active'>
                            <div class='author-heading'>
                                <span class='author-name'>${ author.name }</span>
                                <span class='author-status right'>
                                    <#if author.passed != 0>
                                        <span class='label pass'>${ author.passed }</span>
                                    </#if>
                                    <#if author.failed != 0>
                                        <span class='label fail'>${ author.failed }</span>
                                    </#if>
                                    <#if author.others != 0>
                                        <span class='label others'>${ author.others }</span>
                                    </#if>
						        </span>
                            </div>
                            <div class='author-content hide'>
                                <div class='author-status-counts'>
                                    <#if author.passed != 0><span class='label green accent-4 white-text'>Passed: ${ author.passed }</span></#if>
                                    <#if author.failed != 0><span class='label red lighten-1 white-text'>Failed: ${ author.failed }</span></#if>
                                    <#if author.others != 0><span class='label yellow darken-2 white-text'>Others: ${ author.others }</span></#if>
                                </div>

                                <div class='author-tests'>
                                    <table class='bordered table-results'>
                                        <thead>
                                        <tr>
                                            <th>Timestamp</th>
                                            <th>TestName</th>
                                            <th>Status</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                            <#list author.getTestList() as test>
                                            <tr>
                                                <td>${ test.startTime?datetime?string["${timeStampFormat}"] }</td>
                                                <td class='linked' test-id='${ test.getID() }'>${ test.hierarchicalName }</td>
                                                <td><span class='test-status ${ test.status }'>${ test.status }</span></td>
                                            </tr>
                                            </#list>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </li>
                    </#list>

                </ul>
        </div>
    </div>

    <div class='subview-right left'>
        <div class='view-summary'>
            <h5 class='author-name'></h5>
        </div>
    </div>
</div>
</#if>