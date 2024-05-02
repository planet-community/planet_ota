{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-23.11";
    systems.url = "github:nix-systems/default";
    devenv.url = "github:cachix/devenv";
  };

  nixConfig = {
    extra-trusted-public-keys = "devenv.cachix.org-1:w1cLUi8dv3hnoSPGAuibQv+f9TZLr6cv/Hm9XgU50cw=";
    extra-substituters = "https://devenv.cachix.org";
  };

  outputs = { self, nixpkgs, devenv, systems, ... } @ inputs:
    let
      forEachSystem = nixpkgs.lib.genAttrs (import systems);
    in
    {
      packages = forEachSystem (system: {
        devenv-up = self.devShells.${system}.default.config.procfileScript;
      });

      devShells = forEachSystem
        (system:
          let
            pkgs = nixpkgs.legacyPackages.${system};
          in
          {
            default = devenv.lib.mkShell {
              inherit inputs pkgs;
              modules = [
                {
                  packages = [ pkgs.git ];

                  languages = {
                    java = {
                      enable = true;
                      jdk.package = pkgs.jdk21;
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
                  dotenv.enable = true;
                }
              ];
            };
          });
    };
}
