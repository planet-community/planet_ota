{ lib
, stdenv
, maven
, jdk17
, makeWrapper
, callPackage
}:

let
  repository = callPackage ./.build-maven-repo.nix { };
in

stdenv.mkDerivation rec {
  pname = "planet-ota";
  version = "0.1.0";

  src = ./.;

  nativeBuildInputs = [ maven makeWrapper ];

  buildPhase = ''
    runHook preBuild

    echo "Using repository ${repository}"
    mvn --offline -Dmaven.repo.local=${repository} package;

    runHook postBuild
  '';

  installPhase = ''
    runHook preInstall

    classpath=$(find ${repository} -name "*.jar" -printf ':%h/%f');
    install -Dm644 target/${pname}-${version}.war $out/share/java/${pname}-${version}.war

    makeWrapper ${jdk17}/bin/java $out/bin/${pname} \
      --add-flags "-classpath $out/share/java/${pname}-${version}.war:''${classpath#:}" \
      --add-flags "uk.co.planetcom.infrastructure.ota.server.Application"

    runHook postInstall
  '';
}
