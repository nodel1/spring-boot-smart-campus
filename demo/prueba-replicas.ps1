# prueba-replicas.ps1 - Demo del Taller D. Pide la lista de sensores al balanceador (puerto 8090) cada segundo y
# dice que replica ha contestado (cabecera X-Replica que pone nginx). Ctrl+C para parar.
# Sin tildes a proposito: PowerShell 5.1 lee los .ps1 sin BOM como ANSI.
$Host.UI.RawUI.WindowTitle = 'Demo replicas'
Write-Host 'Pidiendo GET http://localhost:8090/api/sensors al balanceador cada segundo...' -ForegroundColor Cyan
Write-Host ''
$n = 0
while ($true) {
    $n++
    $hora = Get-Date -Format 'HH:mm:ss'
    try {
        $r = Invoke-WebRequest 'http://localhost:8090/api/sensors' -UseBasicParsing -TimeoutSec 5
        $replica = $r.Headers['X-Replica']
        $sensores = @(ConvertFrom-Json $r.Content).Count
        if ($replica -eq 'replica-1') { $color = 'Green' } elseif ($replica -eq 'replica-2') { $color = 'Yellow' } else { $color = 'Magenta' }
        if ($replica -like '*,*') { $replica = "$replica (la primera no contesta: pasa a la otra)" }
        Write-Host ('{0}  #{1,-3} {2}  contesta {3,-10}  {4} sensores' -f $hora, $n, $r.StatusCode, $replica, $sensores) -ForegroundColor $color
    } catch {
        Write-Host ('{0}  #{1,-3} ERROR: {2}' -f $hora, $n, $_.Exception.Message) -ForegroundColor Red
    }
    Start-Sleep -Milliseconds 1200
}
