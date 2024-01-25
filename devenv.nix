{ pkgs, ... }: {
  packages = [ pkgs.git ];

  languages = {
    java = {
        enable = true;
        jdk.package = pkgs.jdk17;
        maven.enable = true;
    };
  };

  services.postgres = {
    enable = true;
    package = pkgs.postgresql;
    listen_addresses = "127.0.0.1";
    initialScript = "CREATE ROLE planet_ota SUPERUSER LOGIN PASSWORD 'password321';";
    initialDatabases = [{ name = "planet_ota_db"; }];
  };

  devcontainer.enable = true;
  difftastic.enable = true;
}
