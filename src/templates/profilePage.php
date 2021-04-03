<!DOCTYPE html>

<html lang="ro">
<head>
    <link rel="stylesheet" href="../resources/css/profilePage.min.css">
    <title>Personal Page</title>
    <?php include "setProfile.php"; ?>
</head>
<body>
    <div class="wizzard">
        <div class="account">
            <h2>Personal Data</h2>
            <div class="row">
                <div class="col_30">
                    <img src = <?php echo $localUser->getImage(); ?> class="profile" ></img>
                </div>
                <div class="row col_70">
                    <div class="col_100">
                        <p>Nume: <?php echo $localUser->getNume(); ?></p>
                    </div>
                    <div class="col_100">
                        <p>Prenume: <?php echo $localUser->getPrenume(); ?></p>
                    </div>
                </div>
            </div>
            <div class="row">
                <div class="col_100">
                    <form action="menu.php">
                        <input type="submit" value="Back" />
                    </form>
                </div>
            </div>               
        </div>
    </div>
</body>
</html>