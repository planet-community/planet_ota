{
  inputs = {
    nixpkgs.url = "github:NixOS/nixpkgs/nixos-23.05";

    flake-utils.url = "github:numtide/flake-utils";

  };
  outputs = { nixpkgs, flake-utils, ... }:

    flake-utils.lib.eachDefaultSystem (system:
      let
        pkgs = import nixpkgs { inherit system; };
        jar = pkgs.callPackage ./.jar.nix { };
      in
      rec {
        devShell = with pkgs; mkShellNoCC {
          name = "java";
          buildInputs = [
            jdk17

            packages.jar
          ];
        };

        packages = {
          inherit jar;
        };

        defaultPackage = packages.jar;
      });
}
