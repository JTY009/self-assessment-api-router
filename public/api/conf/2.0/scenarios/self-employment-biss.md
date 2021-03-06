<p>You can simulate scenarios using Gov-Test-Scenario headers in the sandbox environment.</p>
<p>You can test this endpoint independently, in the sandbox environment, by using any selfEmploymentId that matches the correct regular expression. (You are not required to generate the selfEmploymentId using a previous endpoint.)<p>
<table>
    <thead>
        <tr>
            <th>Header Value (Gov-Test-Scenario)</th>
            <th>Scenario</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <td><p>N/A - Default</p></td>
            <td><p>Simulates the scenario where the client has a net profit.</p></td>
        </tr>
        <tr>
            <td><p>NET_PROFIT</p></td>
            <td><p>Simulates the scenario where the client has a net profit.</p></td>
        </tr>
        <tr>
            <td><p>NET_LOSS</p></td>
            <td><p>Simulates the scenario where the client has a net loss.</p></td>
        </tr>
        <tr>
            <td><p>SELF_EMPLOYMENT_ID_NOT_FOUND</p></td>
            <td><p>Simulates the scenario where a resource can not be found for the self employment ID.</p></td>
        </tr>
        <tr>
             <td><p>NOT_FOUND</p></td>
             <td><p>Simulates the scenario where the endpoint has indicated that no data can be found.</p></td>
        </tr>
        <tr>
              <td><p>NINO_NOT_FOUND</p></td>
              <td><p>Simulates the scenario where the endpoint has indicated that no data can be found for the nino.</p></td>
        </tr>
        <tr>
               <td><p>TAX_YEAR_NOT_FOUND</p></td>
               <td><p>Simulates the scenario where the endpoint has indicated that no data can be found for the tax year.</p></td>
        </tr>
        <tr>
            <td><p>AGENT_NOT_SUBSCRIBED</p></td>
            <td><p>Simulate agent subscription check failure.</p></td>
        </tr>
        <tr>
            <td><p>AGENT_NOT_AUTHORIZED</p></td>
            <td><p>Simulate agent is not authorized by the client to act on their behalf.</p></td>
        </tr>
        <tr>
            <td><p>CLIENT_NOT_SUBSCRIBED</p></td>
            <td><p>Simulate client subscription to MTD check failure.</p></td>
        </tr>
    </tbody>
</table>